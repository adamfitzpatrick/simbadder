package net.muneris.simbadder;

import java.util.List;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;
import net.muneris.simbadder.simbadapi.query.CustomQuery;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all request mappings for this microservice.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 * @see org.springframework.web.bind.annotation.RestController;
 * @see org.springframework.web.bind.annotation.RequestMapping;
 *
 */
@RestController
public class Controller {

	private static final Logger log = Logger.getLogger(Controller.class);
	private Simbad simbad = new Simbad();
	
	/**
	 * Custom query endpoint.  This endpoint does not allow the user to
	 * modify the format of data returned from SIMBAD, but instead receives
	 * a properly formatted JSON object containing all available data fields.
	 * TODO Implement hypermdia api.
	 * @param custom query as a string
	 * @return a list of JSON formatted string objects
	 */
	@RequestMapping(value="/custom-query/{queryString}", method=RequestMethod.GET)
	public List<SimbadObject> getForCustomQuery(@PathVariable(value="queryString") String queryString) {
		CustomQuery query = new CustomQuery(queryString);
		Format format = new Format("CUSTOM_QUERY_FORMAT");
		format.setFields(FormatField.getAll());
		simbad.setFormat(format);
		simbad.setQuery(query);
		List<SimbadObject> objects = simbad.execute();
		log.info("Found " + objects.size() + " objects.");
		return objects;
	}
	
	/**
	 * Currently only implementing a testing api end point.
	 * TODO Implement a hypermedia api.
	 * @return
	 */
	@RequestMapping("/test0")
	public List<SimbadObject> test0() {
		CustomQuery query = new CustomQuery("query coo 0 0 radius=10m");
		Format format = new Format("test");
		format.setFields(FormatField.getAll());
		simbad = new Simbad(query, format);
		List<SimbadObject> objects = simbad.execute();
		log.info("Found " + objects.size() + " objects.");
		return objects;
	}
}
