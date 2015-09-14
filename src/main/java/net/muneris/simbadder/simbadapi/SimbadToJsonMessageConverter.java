package net.muneris.simbadder.simbadapi;

import net.muneris.simbadder.exception.InputMessageNotReadableException;
import net.muneris.simbadder.exception.SimbadExceptionResponseHandler;
import net.muneris.simbadder.model.SimbadObject;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/**
 * Message converter to produce genuine JSON messages.
 *
 * SIMBAD can be coerced into providing a response which looks like JSON, but
 * the returned content type is always Text/Plain, and there are adjustments
 * that must be made to the response to assure readability.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class SimbadToJsonMessageConverter extends AbstractHttpMessageConverter<SimbadObject[]> {

    public static final Logger LOGGER = Logger.getLogger(SimbadToJsonMessageConverter.class);

    private ObjectMapper objectMapper;

    public SimbadToJsonMessageConverter() {
        super(MediaType.TEXT_PLAIN);
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper
                .configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    }

    /**
     * This little utility methods warrants explanation.
     *
     * First, it is worth noting that the character sequence !`! is passed in
     * the formatting string to SIMBAD to serve as string delimiters in the
     * returned data. This sequence was chosen because it is extremely unlikely
     * to appear as a component of the returned data. This particular sequence
     * will therefore appear in the data response to be replaced later.
     *
     * Second, SIMBAD data cannot be formatted properly as an array of comma
     * separated JSON objects. The formatting string passed to Simbad allows it
     * to return a collection of JSON objects without separators. This method
     * starts by enclosing the entire string in square brackets to signify to
     * Jackson that the string is an array. Additionally, all instances in the
     * string where "}{" or "}\n" appear are replaced by "},{" or "},".
     *
     * All double quotes in the data string are escaped (\"). Once all double
     * quotes in the returned data are escaped, the !`! character sequence can
     * be replaced with standard, non-escaped double quotes to deliniate actual
     * returned string data components.
     *
     * There is at least one instance in the SIMBAD database of a double quote
     * being improperly implemented as twin backticks followed by twin single
     * quotes. These are replaced with escaped double quotes.
     *
     * For data that can return arrays, such as OTYPELIST, SIMBAD removes any
     * trailing literals from the format string at the end of the array only. As
     * a result, closing quotes (formatted as !`!) and braces must be repeated
     * outside of the SIMBAD formatting function to properly close array data.
     * As a result, these literals will appear inside the square brackets when
     * SIMBAD provides an empty array response, and must be removed to allow
     * processing.
     *
     * As a final note, it is important to recall that regular expressions in
     * Java must be escaped properly as Java characters so that they can't be
     * passed correctly to the regular expressions engine. Thus, to present
     * something the regexp engine sees as an escaped backslash, it is necessary
     * to present two escaped backslashes to the java compiler. As it relates to
     * this method, the following information is necessary:
     *
     * <pre>
     * Literal String     RegExp String     Java String
     * \                  \\                \\\
     * }                  \}                \\}
     * \"                 \\"               \\\\\"
     * </pre>
     */
    private String transformResponse(String response) {

        String simbadString = "[" + response.trim() + "]";
        // Remove extra string literals from array
        simbadString = simbadString.replaceAll("\\[!`! \\}\\]", "[]");
        simbadString = simbadString.replaceAll("\\}\\{", "},{");
        simbadString = simbadString.replaceAll("\\}\n", "},");
        simbadString = simbadString.replaceAll("\\\"", "\\\\\"");
        simbadString = simbadString.replaceAll("!`!", "\"");
        simbadString = simbadString.replaceAll("``", "\\\\\"");
        return simbadString.replaceAll("''", "\\\\\"");
    }

    @Override
    protected SimbadObject[] readInternal(Class<? extends SimbadObject[]> clazz,
            HttpInputMessage inputMessage) {
        String message = "";
        try {
            message =
                    StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new InputMessageNotReadableException(e);
        }
        String jsonMessage = transformResponse(message);
        try {
            return objectMapper.readValue(jsonMessage, clazz);
        } catch (JsonParseException e) {
            throw new SimbadExceptionResponseHandler(e);
        } catch (JsonMappingException e) {
            throw new SimbadExceptionResponseHandler(e);
        } catch (IOException e) {
            throw new InputMessageNotReadableException(e);
        }
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        if (clazz.equals(SimbadObject[].class)) {
            return true;
        }
        return false;
    }

    @Override
    protected void writeInternal(SimbadObject[] t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        // Not implemented
    }

}
