/**
 * 
 */
package com.amex.pizza.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Mano Ranjan Jayamaran
 *
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
	private Date timestamp;
	private String message;
	private String details;
}
