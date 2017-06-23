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
public enum SimbadExceptionClass {
    
    PARSE_ERROR("ParseException"),
    FORMATTING_ERROR("Incorrect Field In Format"),
    NO_OBJECTS_ERROR("No astronomical object found"),
    UNSPECIFIED_ERROR("Unspecified Error");
    
    private String errorString;
    private static final Map<SimbadExceptionClass, HttpStatus> statusCodes = new HashMap<>();
    static {
        statusCodes.put(SimbadExceptionClass.PARSE_ERROR, HttpStatus.NOT_FOUND);
        statusCodes.put(SimbadExceptionClass.FORMATTING_ERROR, HttpStatus.BAD_REQUEST);
        statusCodes.put(SimbadExceptionClass.UNSPECIFIED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        statusCodes.put(SimbadExceptionClass.NO_OBJECTS_ERROR, HttpStatus.NOT_FOUND);
    }
    private static final Map<String, String> alternateMessages = new HashMap<>();
    static {
        alternateMessages.put("No object found", SimbadExceptionClass.NO_OBJECTS_ERROR.toString());
    }
    
    public static SimbadExceptionClass factory(String errorString) {
        if (alternateMessages.containsKey(errorString)) {
            errorString = alternateMessages.get(errorString);
        }
        Pattern pattern = Pattern.compile(errorString, Pattern.CASE_INSENSITIVE);
        List<SimbadExceptionClass> sEClassList = Arrays.asList(SimbadExceptionClass.values());
        Optional<SimbadExceptionClass> option = sEClassList.stream().filter(sEClass -> {
            return pattern.matcher(sEClass.toString()).find();
        }).findFirst();
        if (option.isPresent()) {
            return option.get();
        }
        return SimbadExceptionClass.UNSPECIFIED_ERROR;
    }
    
    private SimbadExceptionClass(String errorString) {
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