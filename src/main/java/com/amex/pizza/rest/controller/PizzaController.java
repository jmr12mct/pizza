/**
 * 
 */
package com.amex.pizza.rest.controller;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amex.pizza.exception.ResourceNotFoundException;
import com.amex.pizza.rest.domain.PizzaDto;
import com.amex.pizza.service.PizzaService;

/**
 * Rest Controller which provide the APIs to create, update or retrieve Pizzas
 * stored in Pizza table
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	/**
	 * API to return List of Pizzas stored in Pizza table
	 */
	@GetMapping("/pizzas")
	public List<PizzaDto> getPizzas() {
		return pizzaService.getPizzas();
	}

	/**
	 * API to add a Pizza to Pizza table and return the Pizza
	 */
	@PostMapping("/pizzas")
	public PizzaDto addPizza(@Valid @RequestBody PizzaDto pizzaDto) throws ParseException {
		return pizzaService.addPizza(pizzaDto);
	}

	/**
	 * API to update a Pizza and return the updated Pizza
	 */
	@PutMapping("/pizzas/{id}")
	public ResponseEntity<PizzaDto> updatePizza(@PathVariable(value = "id") UUID id,
			@Valid @RequestBody PizzaDto pizzaDto) throws ResourceNotFoundException, ParseException {
		pizzaService.getPizzaById(id); // Invoked to check if the Resource is found, else will throw
										// ResourceNotFoundException
		return ResponseEntity.ok(pizzaService.updatePizza(pizzaDto));
	}

	/**
	 * API to getting and return a Pizza by using Id
	 */
	@GetMapping("/pizzas/{id}")
	public ResponseEntity<PizzaDto> getPizzaById(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(pizzaService.getPizzaById(id));
	}

}
