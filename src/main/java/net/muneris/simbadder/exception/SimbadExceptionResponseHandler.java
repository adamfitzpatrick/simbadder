package net.muneris.simbadder.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="SIMBAD responded with an error message.")
public class SimbadExceptionResponseHandler extends RuntimeException {
	
	private static final Logger log = Logger.getLogger(SimbadExceptionResponseHandler.class);
	private static final long serialVersionUID = 2759986110400827227L;

	private static final String EXCEPTION_PATTERN_STRING = "(?<=(java\\.text\\.))[A-Za-z]{1,50}";
	private static final String MESSAGE_PATTERN_STRING = "(?<=(%s: ))[A-Za-z0-9 :]+";
	private static final String FORMATTING_PATTERN_STRING = "(?<=(incorrect field in format: ))[A-Za-z0-9 ]+";
	
	private Exception e;
	private String exceptionString;
	private String exceptionMessage;
	
	public SimbadExceptionResponseHandler(JsonParseException e) {
		this.e = e;
		throwThis();
	}
	
	public SimbadExceptionResponseHandler(JsonMappingException e) {
		this.e = e;
		if (!parseException()) {
			parseFormattingError();
		}
		selectThrowable();
	}
	
	private boolean parseException() {
		Pattern pattern = Pattern.compile(EXCEPTION_PATTERN_STRING);
		Matcher matcher = pattern.matcher(e.getMessage());
		if (matcher.find()) {
			exceptionString = matcher.group(0);
			parseExceptionMessage();
			return true;
		}
		return false;		
	}
	
	private void parseExceptionMessage() {
		Pattern pattern = Pattern.compile(String.format(MESSAGE_PATTERN_STRING, exceptionString));
		Matcher matcher = pattern.matcher(e.getMessage());
		if (matcher.find()) {
			exceptionMessage = matcher.group(0);
		} else {
			exceptionMessage = "No message from SIMBAD.";
		}
	}
	
	private boolean parseFormattingError() {
		Pattern pattern = Pattern.compile(FORMATTING_PATTERN_STRING);
		Matcher matcher = pattern.matcher(e.getMessage());
		if (matcher.find()) {
			exceptionString = SimbadException.FORMATTING_EXCEPTION;
			exceptionMessage = matcher.group(0);
			return true;
		}
		return false;
	}
	
	private void selectThrowable() {
		if (exceptionString.equals(SimbadException.PARSE_EXCEPTION)) {
			throw new SimbadParseException(exceptionMessage);
		} else if (exceptionString.equals(SimbadException.FORMATTING_EXCEPTION)) {
			throw new SimbadFormattingException(exceptionMessage);
		} else {
			throwThis();
		}
	}
	
	private void throwThis() {
		log.error(e);
	}
}
