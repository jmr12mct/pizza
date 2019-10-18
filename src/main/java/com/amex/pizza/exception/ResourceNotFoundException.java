/**
 * 
 */
package com.amex.pizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents ResourceNotFoundException with 404 Error code raised when a
 * resource is not found
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = -2210370933710289470L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
