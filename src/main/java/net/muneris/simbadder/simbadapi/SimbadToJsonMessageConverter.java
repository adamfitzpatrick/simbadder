package net.muneris.simbadder.simbadapi;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.muneris.simbadder.model.SimbadObject;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

public class SimbadToJsonMessageConverter extends AbstractHttpMessageConverter<SimbadObject[]> {

	private ObjectMapper objectMapper;
	
	public SimbadToJsonMessageConverter() {
		super(MediaType.TEXT_PLAIN);
		objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		if (clazz.equals(SimbadObject[].class)) {
			return true;
		}
		return false;
	}

	@Override
	protected SimbadObject[] readInternal(Class<? extends SimbadObject[]> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		String message = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
		String jsonMessage = transformResponse(message);
		return objectMapper.readValue(jsonMessage, clazz);
	}

	@Override
	protected void writeInternal(SimbadObject[] t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// Not implemented
	}
	
	/**
	 * This little utility methods warrants explanation.  
	 * 
	 * First, it is worth noting that the character sequence !`! is passed in
	 * the formatting string to SIMBAD to serve as string delimiters in the
	 * returned data.  This sequence was chosen because it is extremely unlikely
	 * to appear as a component of the returned data.  This particular sequence
	 * will therefore appear in the data response to be replaced later.
	 * 
	 * Second, SIMBAD data cannot be formatted properly as an array of comma
	 * separated JSON objects.  The formatting string passed to Simbad allows
	 * it to return a collection of JSON objects without separators.  This method
	 * starts by enclosing the entire string in square brackets to signify to
	 * Jackson that the string is an array.  Additionally, all instances in the
	 * string where "}{" or "}\n" appear are replaced by "},{" or "},".
	 * 
	 * All double quotes in the data string are escaped (\").  Once all double
	 * quotes in the returned data are escaped, the !`! character sequence can
	 * be replaced with standard, non-escaped double quotes to deliniate actual
	 * returned string data components.
	 * 
	 * Finally, there is at least one instance in the SIMBAD database of a double
	 * quote being improperly implemented as twin backticks followed by twin
	 * single quotes.  These are replaced with escaped double quotes.
	 * 
	 * As a final note, it is important to recall that regular expressions in
	 * Java must be escaped properly as Java characters so that they can't be
	 * passed correctly to the regular expressions engine.  Thus, to present
	 * something the regexp engine sees as an escaped backslash, it is necessary
	 * to present two escaped backslashes to the java compiler.  As it relates to
	 * this method, the following information is necessary:
	 * 
	 *     Literal String      RegExp String       Java String
	 *     \				   \\				   \\\
	 *     }                   \}                  \\}
	 *     \"				   \\"                 \\\\\"
	 *     
	 */
	private String transformResponse(String response) {

		String simbadString = "[" + response.trim() + "]";
		simbadString = simbadString.replaceAll("\\}\\{", "},{");
		simbadString = simbadString.replaceAll("\\}\n", "},");
		simbadString = simbadString.replaceAll("\\\"", "\\\\\"");
		simbadString = simbadString.replaceAll("!`!", "\"");
		simbadString = simbadString.replaceAll("``", "\\\\\"");
		return simbadString.replaceAll("''", "\\\\\"");
	}

}
