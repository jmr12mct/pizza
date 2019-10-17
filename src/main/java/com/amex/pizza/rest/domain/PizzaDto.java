/**
 * 
 */
package com.amex.pizza.rest.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mano Ranjan Jayamaran
 *
 */

@Data
@NoArgsConstructor
public class PizzaDto implements Serializable {
	
	private UUID id;
	private String name;
	private double price;
	private String description;
}
