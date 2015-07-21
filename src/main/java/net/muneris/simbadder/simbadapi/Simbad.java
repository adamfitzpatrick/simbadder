package net.muneris.simbadder.simbadapi;

import java.io.IOException;
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
import org.springframework.web.client.RestTemplate;

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
	
	/* TODO JavaDoc */
	public Simbad() {
		restTemplate = new RestTemplate();
		SimbadRestInterceptor interceptor = new SimbadRestInterceptor();
		restTemplate.setInterceptors(Collections.singletonList(interceptor));
		mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}
	
	/* TODO JavaDoc */
	public Simbad(Query query, Format format) {
		this();
		this.query = query;
		this.format = format;
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
	 * Executes a query on the SIMBAD api, provided the format
	 * and query members have been defined.
	 * @return response from the SIMBAD server as a JSON object.
	 */
	public List<SimbadObject> execute() {
		if (query == null) {
			log.error("Missing query.");
			return null;
		}
		if (format == null) {
			log.error("Missing format.");
			return null;
		}
		return convert(restTemplate.getForObject(QUERYURL, String.class, format.getFormatString(), query.getQueryString()));
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
		String simbadString = "[" + responseString.trim() + "]";
		simbadString = simbadString.replaceAll("\\}\\{", "},{");
		simbadString = simbadString.replaceAll("\\}\n", "},");
		simbadString = simbadString.replaceAll("\\\"", "\\\\\"");
		simbadString = simbadString.replaceAll("`", "\"");
		try {
			return mapper.readValue(simbadString,
					mapper.getTypeFactory().constructCollectionType(List.class, SimbadObject.class));
		} catch (JsonParseException e) {
			log.error(e);
		} catch (JsonMappingException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}
}
