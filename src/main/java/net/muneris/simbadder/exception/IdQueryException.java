package net.muneris.simbadder.exception;

import org.apache.log4j.Logger;

public class IdQueryException extends RuntimeException {

	private static final long serialVersionUID = -7741881882324033835L;
	private Logger log = Logger.getLogger(IdQueryException.class);
	
	private String message;
	
	public IdQueryException(String message) {
		this.message = message;
		log.error(String.format("IdQuery: %s", message));
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
