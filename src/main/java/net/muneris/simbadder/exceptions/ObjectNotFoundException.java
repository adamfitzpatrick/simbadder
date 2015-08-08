package net.muneris.simbadder.exceptions;

import net.muneris.simbadder.simbadapi.query.Query;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Expected a single object, received multiple objects.")
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7021894259482226111L;

	private static final Logger log = Logger.getLogger(ObjectNotFoundException.class);
	
	public ObjectNotFoundException(Query query) {
		log.error(String.format("No objects found for query '%s.'", query.getQueryString()));
	}
}
