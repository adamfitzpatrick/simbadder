package net.muneris.simbadder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class SimbadFormattingException extends SimbadException {

	private static final long serialVersionUID = 4097038922473653917L;

	public SimbadFormattingException(String message) {
		super(SimbadException.FORMATTING_EXCEPTION + ": " + message);
	}
	
	@Override
	public String getName() {
		return SimbadException.FORMATTING_EXCEPTION;
	}

	@Override
	public String getFullName() {
		return getName();
	}

}
