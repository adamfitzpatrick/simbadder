package net.muneris.simbadder.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@JsonInclude(Include.NON_NULL)
public class ExceptionResponse {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResponse.class);
    private final ExceptionClass exceptionClass;
    private final HttpStatus status;
    private final String message;
    private final String source;
    
    public ExceptionResponse(ExceptionClass exceptionClass, String message, String source) {
        this.exceptionClass = exceptionClass;
        this.status = exceptionClass.getStatus();
        this.message = message;
        this.source = source;
        LOGGER.error("{}: {}. {}", source, exceptionClass.toString(), message);
    }
    
    @JsonProperty("source")
    public String getSource() {
        return source;
    }
    
    @JsonProperty("exception")
    public String getException() {
        return exceptionClass.toString();
    }
    
    
    @JsonProperty("status")
    public HttpStatus getStatus() {
        return status;
    }
    
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}
