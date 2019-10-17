/**
 * 
 */
package com.amex.pizza.service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amex.pizza.exception.ResourceNotFoundException;
import com.amex.pizza.repository.PizzaRepository;
import com.amex.pizza.repository.domain.Pizza;
import com.amex.pizza.rest.domain.PizzaDto;

/**
 * @author Mano Ranjan Jayamaran
 *
 */
@Service
public class PizzaService implements IPizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PizzaDto> getPizzas() {
		List<Pizza> pizzas = pizzaRepository.findAll();
		return pizzas.stream().map(pizza -> convertToDto(pizza)).collect(Collectors.toList());
	}

	@Override
	public PizzaDto addPizza(PizzaDto pizzaDto) throws ParseException {
		Pizza pizza = convertToEntity(pizzaDto);
		return convertToDto(pizzaRepository.save(pizza));
	}

	@Override
	public PizzaDto updatePizza(PizzaDto pizzaDto) throws ParseException {
		Pizza pizza = convertToEntity(pizzaDto);
		return convertToDto(pizzaRepository.save(pizza));
	}

	@Override
	public PizzaDto getPizzaById(UUID id) throws ResourceNotFoundException {
		PizzaDto pizzaDto = convertToDto(pizzaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pizza not found for this id :: " + id)));
		return pizzaDto;
	}

	private PizzaDto convertToDto(Pizza pizza) {
		PizzaDto pizzaDto = modelMapper.map(pizza, PizzaDto.class);
		return pizzaDto;
	}

	private Pizza convertToEntity(PizzaDto pizzaDto) throws ParseException {
		Pizza pizza = modelMapper.map(pizzaDto, Pizza.class);
		return pizza;
	}
}
