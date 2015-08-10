package net.muneris.simbadder.exception;

import org.apache.log4j.Logger;

public abstract class SimbadException extends RuntimeException {

	public static final String EXCEPTION_PREFIX = "java.text.";
	public static final String PARSE_EXCEPTION = "ParseException";
	public static final String FORMATTING_EXCEPTION = "Incorrect field in format";
	
	private static final long serialVersionUID = 30876364741577487L;
	private static final Logger log = Logger.getLogger(SimbadException.class);
	
	protected String message;
	
	public SimbadException(String message) {
		log.error(String.format("SIMBAD %s: %s", getFullName(), message));
		this.message = message;
	}
	
	public abstract String getName();
	public abstract String getFullName();
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
