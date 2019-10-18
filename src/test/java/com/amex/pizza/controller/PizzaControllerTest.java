package com.amex.pizza.controller;

import static com.amex.pizza.util.Constants.DESCRIPTION;
import static com.amex.pizza.util.Constants.ID;
import static com.amex.pizza.util.Constants.NAME;
import static com.amex.pizza.util.Constants.PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.amex.pizza.exception.ResourceNotFoundException;
import com.amex.pizza.rest.controller.PizzaController;
import com.amex.pizza.rest.domain.PizzaDto;
import com.amex.pizza.service.PizzaService;

/**
 * Test class to test all the methods present in Controller using Mockito
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@ExtendWith(MockitoExtension.class)
public class PizzaControllerTest {

	@InjectMocks
	private PizzaController pizzaController;

	@Mock
	private PizzaService pizzaService;

	private PizzaDto mockPizzaDto;

	@BeforeEach
	public void init() {
		mockPizzaDto = createMockPizzaDto();
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Mock and test the GetAllPizzas method
	 */
	@Test
	public void testGetAllPizzas() {
		List<PizzaDto> result = new ArrayList<>();
		result.add(mockPizzaDto);
		when(pizzaService.getPizzas()).thenReturn(result);
		List<PizzaDto> responseExpected = pizzaController.getPizzas();
		assertEquals(1, responseExpected.size());
		assertEquals(NAME, responseExpected.get(0).getName());
		assertEquals(PRICE, responseExpected.get(0).getPrice());
		assertEquals(DESCRIPTION, responseExpected.get(0).getDescription());
	}

	/**
	 * Mock and test the AddPizza method
	 */
	@Test
	public void testAddPizza() throws ParseException {
		when(pizzaService.addPizza(any(PizzaDto.class))).thenReturn(mockPizzaDto);
		PizzaDto responseExpected = pizzaController.addPizza(mockPizzaDto);
		assertEquals(NAME, responseExpected.getName());
		assertEquals(ID, responseExpected.getId().toString());
		assertEquals(PRICE, responseExpected.getPrice());
		assertEquals(DESCRIPTION, responseExpected.getDescription());
	}

	/**
	 * Mock and test the UpdatePizza method
	 */
	@Test
	public void testUpdatePizza() throws ResourceNotFoundException, ParseException {
		UUID id = UUID.fromString(ID);
		when(pizzaService.updatePizza(any(PizzaDto.class))).thenReturn(mockPizzaDto);
		ResponseEntity<PizzaDto> responseExpected = pizzaController.updatePizza(id, mockPizzaDto);
		final PizzaDto updatedPizzaDto = responseExpected.getBody();
		assertEquals(PizzaDto.class, updatedPizzaDto.getClass());
		assertEquals(NAME, updatedPizzaDto.getName());
		assertEquals(ID, updatedPizzaDto.getId().toString());
		assertEquals(PRICE, updatedPizzaDto.getPrice());
		assertEquals(DESCRIPTION, updatedPizzaDto.getDescription());
	}

	/**
	 * Mock and test the GetPizzaById method
	 */
	@Test
	public void testGetPizzaById() throws ResourceNotFoundException {
		UUID id = UUID.fromString(ID);
		when(pizzaService.getPizzaById(any(UUID.class))).thenReturn(mockPizzaDto);
		ResponseEntity<PizzaDto> responseExpected = pizzaController.getPizzaById(id);
		final PizzaDto pizzaDtoById = responseExpected.getBody();
		assertEquals(PizzaDto.class, pizzaDtoById.getClass());
		assertEquals(NAME, pizzaDtoById.getName());
		assertEquals(ID, pizzaDtoById.getId().toString());
		assertEquals(PRICE, pizzaDtoById.getPrice());
		assertEquals(DESCRIPTION, pizzaDtoById.getDescription());
	}

	/**
	 * Mock and test the GetPizzaById method when Resource is not found in DB
	 */
	@Test
	public void testGetPizzaByIdNotFound() throws ResourceNotFoundException {
		UUID id = UUID.fromString(ID);
		when(pizzaService.getPizzaById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ResponseEntity<PizzaDto> responseExpected = pizzaController.getPizzaById(id);
		});
	}

	/**
	 * Mock and test the UpdatePizza method when Resource is not found in DB
	 */
	@Test
	public void testUpdatePizzaNotFound() throws ParseException, ResourceNotFoundException {
		UUID id = UUID.fromString(ID);
		when(pizzaService.getPizzaById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ResponseEntity<PizzaDto> responseExpected = pizzaController.updatePizza(id, mockPizzaDto);
		});
	}

	/**
	 * Method to create test PizzaDto object
	 */
	private PizzaDto createMockPizzaDto() {
		UUID id = UUID.fromString(ID);
		PizzaDto mockPizzaDto = new PizzaDto();
		mockPizzaDto.setId(id);
		mockPizzaDto.setName(NAME);
		mockPizzaDto.setPrice(PRICE);
		mockPizzaDto.setDescription(DESCRIPTION);
		return mockPizzaDto;
	}

}
