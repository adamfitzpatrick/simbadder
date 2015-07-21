package net.muneris.simbadder;

import java.util.List;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;
import net.muneris.simbadder.simbadapi.query.CustomQuery;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private Simbad simbad;
	
	public Controller() {
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
