package net.muneris.simbadder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown by {@link SimbadExceptionResponseHandler} when SIMBAD responds
 * with an error indicating it is unable to parse the request information
 * it received.  This can be interpreted as a lower-level error than would
 * be returned as a result of improper formatting or query information.  In
 * other words, SIMBAD couldn't even get as far as distinguishing format and
 * query request components.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SimbadParseException extends SimbadException {

    private static final long serialVersionUID = -7950490740707750722L;

    public SimbadParseException(String message) {
        super(message);
    }

    @Override
    public String getFullName() {
        return SimbadException.EXCEPTION_PREFIX + SimbadException.PARSE_EXCEPTION;
    }

    @Override
    public String getName() {
        return SimbadException.PARSE_EXCEPTION;
    }
}
