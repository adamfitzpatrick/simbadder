package net.muneris.simbadder.simbadapi;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.query.Query;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * This class is responsible for hitting the apis at simbad.u-strasbg.fr given
 * specific query and format parameters.
 * 
 * SIMBAD features a number of different api endpoints that are chosen primarily
 * on the nature of query to be provided.  Several of these endpoints provide
 * simplified access to a specific type of query.  The most recommended endpoint
 * allows for queries to be submitted by script, and can fetch any and all data
 * that the other end points are able to obtain.  Given that this project is
 * intended to provide a simplified interface to SIMBAD as well as to return
 * more useful data formats, only the scripting query is implemented here. The 
 * specialized query endpoints offered by SIMBAD are not required.
 * 
 * There are three distinct types of queries that may be submitted to the api:
 * <ul>
 * 	<li>ID queries search by object identifier</li>
 *  <li>Coordinate queries search by and around coordinates</li>
 *  <li>Sample queries accept a list of conditions, such as
 *  maximum magnitudes or coordinate ranges.  This provides the
 *  greatest flexibility, although it is also the most challenging to configure.</li>
 * </ul>
 * The exact request made of the SIMBAD api is dictated by the content of the query
 * member of this class.
 * 
 * The class includes a format member which provides a formatting instruction string
 * to the SIMBAD api, with the intent of ensuring the content returned from SIMBAD
 * will be properly formatted JSON.
 * 
 * @author Adam Fitzptrick (adam@muneris.net)
 * @see net.muneris.simbadder.simbadapi.formatting.Format
 * @see net.muneris.simbadder.simbadapi.queries.Query
 *
 */

public class Simbad {
	/* TODO Implement Query class(es)*/
	
	private static final Logger log = Logger.getLogger(Simbad.class);
	
	/** The main api url that this microservice utilizes to request data from SIMBAD. */
	private static final String QUERYURL = "http://simbad.u-strasbg.fr/simbad/sim-script?script=output"
			+ " console=off script=off\n{format}\n{query}";
	/**
	 * Determines the formatting specifier that will be provided to SIMBAD.
	 * The formatting specifier dictates exactly what information is returned
	 * by simbad and how it is presented.
	 * @see net.muneris.simbadder.simbadapi.formatting
	 */
	private Format format;
	/** The actual query submitted to SIMBAD. */
	private Query query;
	/** Spring class for consuming REST services. */
	private RestTemplate restTemplate;
	/** Jackson object mapper to convert string response to JSON. */
	private ObjectMapper mapper;
	/** Object to track unmodified response string. */
	private ResponseEntity<String> response;
	
	/* TODO JavaDoc */
	public Simbad() {
		restTemplate = new RestTemplate();
		SimbadRestInterceptor interceptor = new SimbadRestInterceptor();
		restTemplate.setInterceptors(Collections.singletonList(interceptor));
		mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
	}
	
	/* TODO JavaDoc */
	public Simbad(Query query, Format format) {
		this();
		this.setQuery(query);
		this.setFormat(format);
	}
	
	/** Sets the format object for this instance.*/
	public void setFormat(Format format) {
		this.format = format;
	}
	
	/** Retrieves the format object for this instance.*/
	public Format getFormat() {
		return format;
	}
	
	/** Sets the query object for this instance.*/
	public void setQuery(Query query) {
		this.query = query;
	}
	
	/** Retrieves the query object for this instance.*/
	public Query getQuery() {
		return query;
	}
	
	/**
	 * Method utilized to send a request to SIMBAD when the response is expected
	 * to contain a single astronomical object.  Warns if the response does not
	 * contain exactly one object.
	 * @return A single astronomical object.
	 */
	public ResponseEntity<SimbadObject> getOne() {
		if (execute()) {
			List<SimbadObject> objects = convert(response.getBody());
			if (objects.size() > 1) {
				log.warn(String.format("Found %s objects. Returning first object found.", objects.size()));
			} else if (objects.size() == 0) {
				log.warn("No objects found.");
				return new ResponseEntity<SimbadObject>(new SimbadObject(), HttpStatus.OK);
			}
			return new ResponseEntity<SimbadObject>(objects.get(0), response.getStatusCode());
		}
		return null;
	}
	
	/**
	 * Mehod Utilized to send a request to SIMBAD when the response is expected
	 * to contain one or more objects.  Warns if zero objects are found.
	 * @return A list of astronomical objects.
	 */
	public ResponseEntity<List<SimbadObject>> getMany() {
		if (execute()) {
			List<SimbadObject> objects = convert(response.getBody());
			if (objects.size() == 0) {
				log.warn("No objects found.");
			} else {
				log.info(String.format("Found %s objects.", objects.size()));
			}
			return new ResponseEntity<List<SimbadObject>>(objects, response.getStatusCode()); 
		}
		return null;
	}
	
	/**
	 * Executes a query on the SIMBAD api if it can be verified as properly
	 * formatted.
	 * @return response from the SIMBAD server as a single JSON object.
	 */
	private boolean execute() {
		if (verify()) {
			URI uri = new UriTemplate(QUERYURL).expand(format.getFormatString(), query.getQueryString());
			RequestEntity<String> request = new RequestEntity<String>(HttpMethod.GET, uri);
			response = restTemplate.exchange(request, String.class);
			return true;
		}
		return false;
	}
	
	/**
	 * Verifies that all components required for a well-formatted request to
	 * SIMBAD are available.
	 * @return boolean indicating that the request is ready
	 */
	private boolean verify() {
		if (query == null) {
			log.error("Missing query.");
			return false;
		}
		if (format == null) {
			log.error("Missing format.");
			return false;
		}
		return true;
	}
	
	/**
	 * In lieu of a dedicated class for converting incoming messages from simbad,
	 * This method uses Jackson's ObjectMapper class to convert the message
	 * directly to the desired list of SimbadObjects.  Refer to SimbadObject and
	 * related classes as well as FormatField to see how formatting is configured
	 * with SIMBAD.
	 * @param SIMBAD response formatted as a string
	 * @return list of POJOs specific to SIMBAD data
	 * @see com.fasterxml.jackson.databind.ObjectMapper
	 * @see net.muneris.simbadder.model.SimbadObject
	 * @see net.muneris.simbadder.simbadapi.formatting.FormatField
	 */
	private List<SimbadObject> convert(String responseString) {
		String simbadString = transformResponse(responseString);
		try {
			return mapper.readValue(simbadString,
					mapper.getTypeFactory().constructCollectionType(List.class, SimbadObject.class));
		} catch (JsonParseException e) {
			SimbadObject object = SimbadObject.fromError(responseString);
			response = new ResponseEntity<String>(responseString, HttpStatus.BAD_REQUEST);
			return Arrays.asList(object);
		} catch (JsonMappingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return null;
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
