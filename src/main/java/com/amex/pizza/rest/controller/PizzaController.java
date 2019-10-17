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
 * @author Mano Ranjan Jayamaran
 *
 */

@RestController
@RequestMapping("/api/v1")
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	@GetMapping("/pizzas")
	public List<PizzaDto> getPizzas() {
		return pizzaService.getPizzas();
	}

	@PostMapping("/pizzas")
	public PizzaDto addPizza(@Valid @RequestBody PizzaDto pizzaDto) throws ParseException {
		return pizzaService.addPizza(pizzaDto);
	}

	@PutMapping("/pizzas/{id}")
	public ResponseEntity<PizzaDto> updatePizza(@PathVariable(value = "id") UUID id,
			@Valid @RequestBody PizzaDto pizzaDto) throws ResourceNotFoundException, ParseException {
		pizzaService.getPizzaById(id);
		return ResponseEntity.ok(pizzaService.updatePizza(pizzaDto));
	}

	@GetMapping("/pizzas/{id}")
	public ResponseEntity<PizzaDto> getPizzaById(@PathVariable(value = "id") UUID id) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(pizzaService.getPizzaById(id));
	}

}
