package net.muneris.simbadder.exception;

import org.apache.log4j.Logger;

/**
 * Thrown when identifier query parameters are unreadable or improperly
 * formatted.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class IdQueryException extends RuntimeException {

    private static final long serialVersionUID = -7741881882324033835L;
    private static final Logger LOGGER = Logger.getLogger(IdQueryException.class);

    private String message;
    
    public IdQueryException(String message) {
        this.message = message;
        LOGGER.error(String.format("IdQuery: %s", message));
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
