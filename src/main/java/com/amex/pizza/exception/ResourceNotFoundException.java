/**
 * 
 */
package com.amex.pizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mano Ranjan Jayamaran
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = -2210370933710289470L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
