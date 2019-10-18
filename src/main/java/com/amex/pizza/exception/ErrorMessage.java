/**
 * 
 */
package com.amex.pizza.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the Error message that will get displayed in response on failure
 * Lombok will generate Setters and Getters as well as an All argument
 * constructor
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
	private Date timestamp;
	private String message;
	private String details;
}
