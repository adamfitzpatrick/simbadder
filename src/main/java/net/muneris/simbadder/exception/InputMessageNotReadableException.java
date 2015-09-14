package net.muneris.simbadder.exception;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the response from SIMBAD cannot be parsed.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
reason = "SIMBAD returned an unreadable response.")
public class InputMessageNotReadableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger
            .getLogger(InputMessageNotReadableException.class);

    public InputMessageNotReadableException(IOException e) {
        LOGGER.error(e);
    }

}
