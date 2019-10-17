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
 * @author Mano Ranjan Jayamaran
 *
 */
public interface IPizzaService {

	List<PizzaDto> getPizzas();

	PizzaDto addPizza(PizzaDto pizzaDto) throws ParseException;

	PizzaDto updatePizza(PizzaDto pizzaDto) throws ParseException;

	PizzaDto getPizzaById(UUID id) throws ResourceNotFoundException;
}
