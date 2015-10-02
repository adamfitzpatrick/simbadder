package net.muneris.simbadder.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({ "localizedMessage", "stackTrace", "suppressed" })
public class SimbadderException extends RuntimeException {
    
    private static final long serialVersionUID = 5194177718541035958L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SimbadderException.class);
    
    private final Long timestamp;
    private final String reason;
    private final HttpStatus status;
    private final String message;
    private final String source;
    
    public SimbadderException(SimbadExceptionClass exceptionClass, String message, String source) {
        this(exceptionClass.toString(), message, source, exceptionClass.getStatus());
    }
    
    public SimbadderException(String reason, String message, String source, HttpStatus status) {
        this.timestamp = System.currentTimeMillis();
        this.reason = reason;
        this.message = message;
        this.source = source;
        this.status = status;
        publishLog();
    }
    
    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }
    
    @JsonProperty("source")
    public String getSource() {
        return source;
    }
    
    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }
    
    @JsonProperty("status")
    public int getStatusValue() {
        return status.value();
    }
    
    @JsonProperty("error")
    public String getError() {
        return status.getReasonPhrase();
    }
    
    @Override
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
    
    @JsonIgnore
    public HttpStatus getStatus() {
        return status;
    }
    
    
    private void publishLog() {
        LOGGER.error("{}: {}. {}", source, reason, message);
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SimbadderException)) {
            return false;
        }
        SimbadderException exception = (SimbadderException) object;
        boolean check = this.getReason().equals(exception.getReason());
        check = check && this.getMessage().equals(exception.getMessage());
        check = check && this.getSource().equals(exception.getSource());
        return check && this.getStatus().equals(exception.getStatus());
    }
}
