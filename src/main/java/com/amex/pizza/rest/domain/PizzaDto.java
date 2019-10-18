/**
 * 
 */
package com.amex.pizza.rest.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Pizza
 * 
 * Lombok will generate Getters and Setters as well as No Argument Constructor
 * 
 * No Argument Constructor is provided to enable ModelMapper to map domain and
 * entity objects
 * 
 * Used Double for Price instead of BigDecimal since Precision is not required
 * here
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@Data
@NoArgsConstructor
public class PizzaDto implements Serializable {

	private UUID id;
	private String name;
	private double price;
	private String description;
}
