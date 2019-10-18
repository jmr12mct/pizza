/**
 * 
 */
package com.amex.pizza.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * All Excpetion thrown from Controller are captured here and proper Error
 * message is constructed
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @param ResourceNotFoundException
	 * @param WebRequest
	 * @return ResponseEntity holding Not Found Error message
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}

	/**
	 * @param Exception
	 * @param WebRequest
	 * @return ResponseEntity holding Internal Server Error message
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
