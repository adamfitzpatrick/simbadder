package net.muneris.simbadder.simbadapi;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * This class provides a method for intercepting outgoing requests and logging
 * the URI exactly as passed to the api.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class SimbadRestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger log = Logger.getLogger(Simbad.class);
	
	/** Outgoing request interceptor primarily for the purpose of request logging. */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		log.info("SIMBAD request:" + request.getURI());
		return execution.execute(request, body);
	}

}
