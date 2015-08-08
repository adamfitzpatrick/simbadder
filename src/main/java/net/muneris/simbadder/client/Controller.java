package net.muneris.simbadder.client;

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
import org.springframework.http.HttpStatus;
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
	
	// TODO Implement Mockito so this class can be properly tested with mocked static methods.
	
	private static final Logger log = Logger.getLogger(Controller.class);
	
	/**
	 * Identifier query endpoint for array responses.
	 * @param idListString Comma separated list of identifiers
	 * @return list of JSON formatted astronomical objects
	 */
	@RequestMapping(value="/idquery/{idListString}", method=RequestMethod.GET)
	public ResponseEntity<List<SimbadObject>> getForIdListQuery(@PathVariable(value="idListString") String[] idListString) {
		IdQuery query = new IdQuery(Arrays.asList(idListString));
		List<SimbadObject> objects = ResponseAssembler
				.assembleList(new Simbad(query, Format.allNonDistance()));
		return new ResponseEntity<List<SimbadObject>>(objects, HttpStatus.OK);
	}
	
	/**
	 * Identifier query endpoint for a singular.  Accepts only a single 
	 * identifier.
	 * @param id Single identifier string
	 * @return A single JSON formatted astronomical object.
	 */
	@RequestMapping(value="/idquery/single/{id}", method=RequestMethod.GET)
	public ResponseEntity<SimbadObject> getForIdQuery(@PathVariable(value="id") String id) {
		IdQuery query = new IdQuery(id);
		SimbadObject object = ResponseAssembler
				.assembleSingle(new Simbad(query, Format.allNonDistance()));
		return new ResponseEntity<SimbadObject>(object, HttpStatus.OK);
	}
	
	/**
	 * Custom query endpoint.
	 * @param queryString custom query as a string
	 * @return list of JSON formatted astronomical objects
	 */
	@RequestMapping(value="/custom-query/{queryString}", method=RequestMethod.GET)
	public HttpEntity<List<SimbadObject>> getForCustomQuery(@PathVariable(value="queryString") String queryString) {
		CustomQuery query = new CustomQuery(queryString);
		List<SimbadObject> objects = ResponseAssembler
				.assembleList(new Simbad(query, Format.allNonDistance()));
		return new ResponseEntity<List<SimbadObject>>(objects, HttpStatus.OK);
	}
}
