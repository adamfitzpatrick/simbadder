package net.muneris.simbadder.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Allows logging of all requests immediately prior to passing them to SIMBAD,
 * and all responses immediately prior to processing from SIMBAD.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@Component
public class LoggingFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class);

    private HttpServletRequestWrapper requestWrapper;
    private String requestUri;

    @Override
    public void destroy() {
        LOGGER.info("Removing loggingFilter from application context...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        preFilter(request);
        chain.doFilter(request, response);
        postFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Initializing loggingFilter...");
    }

    private String getLogString(String requestUri, Integer statusCode) {
        String statusStr = "";
        if (statusCode != null) {
            statusStr =
                    String.format(" with %s (%s)", statusCode, HttpStatus.valueOf(statusCode)
                            .name());
        }
        return String.format("request for resource %s%s", requestUri, statusStr);
    }

    private void postFilter(ServletRequest request, ServletResponse response) {
        HttpServletResponseWrapper responseWrapper =
                new HttpServletResponseWrapper((HttpServletResponse) response);
        LOGGER.info("Completed " + getLogString(requestUri, responseWrapper.getStatus()));
    }

    private void preFilter(ServletRequest request) {
        requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) request);
        String query = "";
        if (requestWrapper.getQueryString() != null) {
            query = "?" + requestWrapper.getQueryString();
        }
        requestUri = requestWrapper.getRequestURI() + query;
        LOGGER.info("Received " + getLogString(requestUri, null));
    }
}
