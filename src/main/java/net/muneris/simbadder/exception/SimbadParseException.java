package net.muneris.simbadder.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class SimbadParseException extends SimbadException {

	private static final Logger log = Logger.getLogger(SimbadParseException.class);
	private static final long serialVersionUID = -7950490740707750722L;

	public SimbadParseException(String message) {
		super(message);
	}

	@Override
	public String getName() {
		return SimbadException.PARSE_EXCEPTION;
	}

	@Override
	public String getFullName() {
		return SimbadException.EXCEPTION_PREFIX + SimbadException.PARSE_EXCEPTION;
	}
}
