package net.muneris.simbadder.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public enum ExceptionClass {
    
    PARSE_ERROR("ParseException"),
    FORMATTING_ERROR("Incorrect Field In Format"),
    IDQUERY_EXCEPTION("IdQueryException"),
    UNSPECIFIED_ERROR("Unspecified Error");
    
    private String errorString;
    private static final Map<ExceptionClass, HttpStatus> statusCodes = new HashMap<>();
    static {
        statusCodes.put(ExceptionClass.PARSE_ERROR, HttpStatus.NOT_FOUND);
        statusCodes.put(ExceptionClass.FORMATTING_ERROR, HttpStatus.BAD_REQUEST);
        statusCodes.put(ExceptionClass.UNSPECIFIED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        statusCodes.put(ExceptionClass.IDQUERY_EXCEPTION, HttpStatus.BAD_REQUEST);
    }
    
    public static ExceptionClass factory(String errorString) {
        Pattern pattern = Pattern.compile(errorString, Pattern.CASE_INSENSITIVE);
        List<ExceptionClass> sEClassList = Arrays.asList(ExceptionClass.values());
        Optional<ExceptionClass> option = sEClassList.stream().filter(sEClass -> {
            return pattern.matcher(sEClass.toString()).find();
        }).findFirst();
        if (option.isPresent()) {
            return option.get();
        }
        return ExceptionClass.UNSPECIFIED_ERROR;
    }
    
    private ExceptionClass(String errorString) {
        this.errorString = errorString;
    }
    
    @Override
    public String toString() {
        return errorString;
    }
    
    public HttpStatus getStatus() {
        return statusCodes.get(this);
    }
}
