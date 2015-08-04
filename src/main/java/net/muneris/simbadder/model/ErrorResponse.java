package net.muneris.simbadder.model;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private String responseString;
	private HttpStatus responseStatus;
	
	public ErrorResponse(String responseBody, HttpStatus status) {
		this.responseStatus = status;
		this.responseString = responseBody;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public HttpStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(HttpStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
}
