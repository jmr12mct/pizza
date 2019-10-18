/**
 * 
 */
package com.amex.pizza.service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import com.amex.pizza.exception.ResourceNotFoundException;
import com.amex.pizza.rest.domain.PizzaDto;

/**
 * Provides method implementations for Service Class
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */
public interface IPizzaService {

	List<PizzaDto> getPizzas();

	PizzaDto addPizza(PizzaDto pizzaDto) throws ParseException;

	PizzaDto updatePizza(PizzaDto pizzaDto) throws ParseException;

	PizzaDto getPizzaById(UUID id) throws ResourceNotFoundException;
}
