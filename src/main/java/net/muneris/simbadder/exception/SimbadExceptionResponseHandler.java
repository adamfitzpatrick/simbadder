package net.muneris.simbadder.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the response from SIMBAD incorporates an error message.
 *
 * Error messages from SIMBAD are never properly formatted JSON, and will
 * therefore cause the
 * {@link net.muneris.simbadder.simbadapi.SimbadToJsonMessageConverter} to throw
 * a JSON-related exception. This class is intended to catch those exceptions
 * and parse the messages into meaningful representations of SIMBAD's complaint.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "SIMBAD responded with an error.")
public class SimbadExceptionResponseHandler extends RuntimeException {

    private static final Logger LOGGER = Logger
            .getLogger(SimbadExceptionResponseHandler.class);
    private static final long serialVersionUID = 2759986110400827227L;

    private static final String EXCEPTION_PATTERN_STRING =
            "(?<=(java\\.text\\.))[A-Za-z]{1,50}";
    private static final String MESSAGE_PATTERN_STRING = "(?<=(%s: ))[A-Za-z0-9 :]+";
    private static final String FORMATTING_PATTERN_STRING =
            "(?<=(incorrect field in format: ))[A-Za-z0-9 ]+";

    private Exception e;
    private String exceptionString;
    private String exceptionMessage;

    public SimbadExceptionResponseHandler(JsonMappingException e) {
        this.e = e;
        if (!parseException()) {
            parseFormattingError();
        }
        selectThrowable();
    }

    public SimbadExceptionResponseHandler(JsonParseException e) {
        this.e = e;
        throwThis();
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
        Pattern pattern =
                Pattern.compile(String.format(MESSAGE_PATTERN_STRING, exceptionString));
        Matcher matcher = pattern.matcher(e.getMessage());
        if (matcher.find()) {
            exceptionMessage = matcher.group(0);
        } else {
            exceptionMessage = "No message from SIMBAD or message not readable.";
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
        LOGGER.error(e);
    }
}
