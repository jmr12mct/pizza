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
 * Service Class that interacts with DAO layer to create or get entities.
 * 
 * Also it converts Entity object to DTO and vice versa using ModelMapper
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
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
	public PizzaDto addPizza(final PizzaDto pizzaDto) throws ParseException {
		Pizza pizza = convertToEntity(pizzaDto);
		return convertToDto(pizzaRepository.save(pizza));
	}

	@Override
	public PizzaDto updatePizza(final PizzaDto pizzaDto) throws ParseException {
		Pizza pizza = convertToEntity(pizzaDto);
		return convertToDto(pizzaRepository.save(pizza));
	}

	@Override
	public PizzaDto getPizzaById(final UUID id) throws ResourceNotFoundException {
		return convertToDto(pizzaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pizza not found for this id :: " + id)));
	}

	/**
	 * Method to convert Pizza Entity object to Pizza DTO object
	 * 
	 * @param Pizza
	 * @return PizzaDto
	 */
	private PizzaDto convertToDto(final Pizza pizza) {
		return modelMapper.map(pizza, PizzaDto.class);
	}

	/**
	 * Method to convert Pizza DTO object to Pizza entity object
	 * 
	 * @param PizzaDto
	 * @return Pizza
	 */
	private Pizza convertToEntity(final PizzaDto pizzaDto) {
		return modelMapper.map(pizzaDto, Pizza.class);
	}
}
