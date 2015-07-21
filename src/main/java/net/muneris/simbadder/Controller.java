package net.muneris.simbadder;

import java.util.List;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.Simbad;
import net.muneris.simbadder.simbadapi.formatting.Format;
import net.muneris.simbadder.simbadapi.formatting.FormatField;

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
		simbad = new Simbad();
	}
	
	/**
	 * Currently only implementing a testing api end point.
	 * TODO Implement a hypermedia api.
	 * @return
	 */
	@RequestMapping("/test0")
	public List<SimbadObject> test0() {
		Format format = new Format("test");
		format.setFields(FormatField.getAll());
		simbad.setFormat(format);
		List<SimbadObject> objects = simbad.runQuery();
		log.info("Found " + objects.size() + " objects.");
		return objects;
	}
}
