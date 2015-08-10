package net.muneris.simbadder.exception;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="SIMBAD returned an unreadable resposne.")
public class InputMessageNotReadableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(InputMessageNotReadableException.class);
	
	public InputMessageNotReadableException(IOException e) {
		log.error(e);
	}

}
