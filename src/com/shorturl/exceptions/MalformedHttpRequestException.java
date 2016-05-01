/**
 * 
 */
package com.shorturl.exceptions;

/**
 * Represents a web request malformed by the client
 * @author Pradeep Kumar
 */
@SuppressWarnings("serial")
public class MalformedHttpRequestException extends RuntimeException {
	public MalformedHttpRequestException() {
		super();
	}

	public MalformedHttpRequestException(String message) {
		super(message);
	}
}
