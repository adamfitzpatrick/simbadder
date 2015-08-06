package net.muneris.simbadder;

import java.util.Arrays;
import java.util.List;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;
import net.muneris.simbadder.simbadapi.query.CustomQuery;
import net.muneris.simbadder.simbadapi.query.IdQuery;
import net.muneris.simbadder.simbadapi.query.Query;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contains all request mappings for this microservice.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@RestController
public class Controller {
	
	// TODO Add error handling methods, possibly not in this class.
	
	private static final Logger log = Logger.getLogger(Controller.class);
	
	/**
	 * Identifier query endpoint for array responses.
	 * @param idListString Comma separated list of identifiers
	 * @return list of JSON formatted astronomical objects
	 */
	@RequestMapping(value="/idquery/{idListString}", method=RequestMethod.GET)
	public HttpEntity<List<SimbadObject>> getForIdListQuery(@PathVariable(value="idListString") String[] idListString) {
		IdQuery query = new IdQuery(Arrays.asList(idListString));
		return sendListQuery(query);
	}
	
	/**
	 * Identifier query endpoint for a singular.  Accepts only a single 
	 * identifier.
	 * @param id Single identifier string
	 * @return A single JSON formatted astronomical object.
	 */
	@RequestMapping(value="/idquery/single/{id}", method=RequestMethod.GET)
	public HttpEntity<SimbadObject> getForIdQuery(@PathVariable(value="id") String id) {
		IdQuery query = new IdQuery(id);
		return sendQuery(query);
	}
	
	/**
	 * Custom query endpoint.
	 * @param queryString custom query as a string
	 * @return list of JSON formatted astronomical objects
	 */
	@RequestMapping(value="/custom-query/{queryString}", method=RequestMethod.GET)
	public HttpEntity<List<SimbadObject>> getForCustomQuery(@PathVariable(value="queryString") String queryString) {
		CustomQuery query = new CustomQuery(queryString);
		return sendListQuery(query);
	}
	
	/**
	 * Currently only implementing a testing api end point.
	 * TODO Implement a hypermedia api.
	 * @return list of JSON formatted astronomical objects
	 */
	@RequestMapping("/test0")
	public HttpEntity<List<SimbadObject>> test0() {
		CustomQuery query = new CustomQuery("query coo 0 0 radius=10m");
		return sendListQuery(query);
	}
	
	/**
	 * Convenience method to submit a query to SIMBAD.  Does not allow user to
	 * modify the format of data returned from SIMBAD, but instead receives
	 * a list of properly formatted JSON objects containing all available data
	 * fields.
	 * @param query
	 * @return list of JSON formatted astronomical objects
	 */
	private ResponseEntity<List<SimbadObject>> sendListQuery(Query query) {
		Format format = new Format("FORMATTING_STRING");
		format.setFields(FormatField.getAllNotDist());;
		Simbad simbad = new Simbad(query, format);
		return simbad.getMany();
	}
	
	/**
	 * Convenience method to submit a query to SIMBAD which only allows a
	 * single object response.  If more than one object is returned by SIMBAD,
	 * this method logs a warning and returns the first item found. Similar to
	 * {@link #sendListQuery(Query) sendListQuery}, in that formatted cannot be
	 * specified.
	 * @param query
	 * @return A single JSON formatted astronomical object
	 */
	private ResponseEntity<SimbadObject> sendQuery(Query query) {
		Format format = new Format("FORMATTING_STRING");
		format.setFields(FormatField.getAllNotDist());
		Simbad simbad = new Simbad(query, format);
		return simbad.getOne();
	}
}
