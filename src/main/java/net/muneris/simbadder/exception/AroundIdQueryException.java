package net.muneris.simbadder.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class AroundIdQueryException extends RuntimeException {

	private static final long serialVersionUID = 5493051625500544999L;
	private static final Logger log = Logger.getLogger(AroundIdQueryException.class);
	
	private String message;
	
	public AroundIdQueryException(String message) {
		this.message = message;
		log.error(String.format("AroundIdQuery: %s", message));
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
