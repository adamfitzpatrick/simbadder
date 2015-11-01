package net.muneris.simbadder.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

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
@ControllerAdvice
public class SimbadderExceptionHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SimbadderExceptionHandler.class);
    
    private static final String EXCEPTION_PATTERN_STRING =
            "(?<=(java\\.text\\.))[A-Za-z]{1,50}|"
            + "incorrect field in format|"
            + "No (astronomical )?object found";
    private static final String MESSAGE_PATTERN_STRING = "(?<=(%s: ))[A-Za-z0-9 :]+";

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseBody
    public ResponseEntity<SimbadderException> resolveException(ResourceAccessException ex) {
        SimbadExceptionClass exceptionClass = parseSimbadExceptionClass(ex.getMessage());
        String message = parseExceptionMessage(exceptionClass, ex.getMessage());
        LOGGER.error("SIMBAD relayed error ({}): {}", exceptionClass, message);
        SimbadderException response =
                new SimbadderException(exceptionClass, message, "Relayed from SIMBAD");
        return new ResponseEntity<SimbadderException>(response, response.getStatus());
    }
    
    @ExceptionHandler(SimbadderException.class)
    @ResponseBody
    public ResponseEntity<SimbadderException> resolveException(SimbadderException ex) {
        return new ResponseEntity<SimbadderException>(ex, ex.getStatus());
    }

    private SimbadExceptionClass parseSimbadExceptionClass(String message) {
        Pattern pattern = Pattern.compile(EXCEPTION_PATTERN_STRING);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return SimbadExceptionClass.factory(matcher.group(0));
        }
        return SimbadExceptionClass.UNSPECIFIED_ERROR;
    }

    private String parseExceptionMessage(SimbadExceptionClass exceptionClass, String message) {
        if (exceptionClass == null) {
            return message;
        }
        String patternStr = String.format(MESSAGE_PATTERN_STRING, exceptionClass.toString());
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return "No additional information from SIMBAD.";
        }
    }
}
