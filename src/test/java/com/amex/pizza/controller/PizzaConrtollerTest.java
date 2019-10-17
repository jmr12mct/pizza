/**
 * 
 */
package com.amex.pizza.controller;

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
 * @author Mano Ranjan Jayamaran
 *
 */

@ExtendWith(MockitoExtension.class)
public class PizzaConrtollerTest {

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

	@Test
	public void testGetAllPizzas() {
		List<PizzaDto> result = new ArrayList<>();
		result.add(mockPizzaDto);
		when(pizzaService.getPizzas()).thenReturn(result);
		List<PizzaDto> responseExpected = pizzaController.getPizzas();
		assertEquals(1, responseExpected.size());
		assertEquals("Tikka", responseExpected.get(0).getName());
	}

	@Test
	public void testAddPizza() throws ParseException {
		when(pizzaService.addPizza(any(PizzaDto.class))).thenReturn(mockPizzaDto);
		PizzaDto responseExpected = pizzaController.addPizza(mockPizzaDto);
		assertEquals("Tikka", responseExpected.getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.getId().toString());
	}

	@Test
	public void testUpdatePizza() throws ResourceNotFoundException, ParseException {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		when(pizzaService.updatePizza(any(PizzaDto.class))).thenReturn(mockPizzaDto);
		ResponseEntity<PizzaDto> responseExpected = pizzaController.updatePizza(id, mockPizzaDto);
		assertEquals(PizzaDto.class, responseExpected.getBody().getClass());
		assertEquals("Tikka", responseExpected.getBody().getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.getBody().getId().toString());
	}

	@Test
	public void testGetPizzaById() throws ResourceNotFoundException {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		when(pizzaService.getPizzaById(any(UUID.class))).thenReturn(mockPizzaDto);
		ResponseEntity<PizzaDto> responseExpected = pizzaController.getPizzaById(id);
		assertEquals(PizzaDto.class, responseExpected.getBody().getClass());
		assertEquals("Tikka", responseExpected.getBody().getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.getBody().getId().toString());
	}

	@Test
	public void testGetPizzaByIdNotFound() throws ResourceNotFoundException {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		when(pizzaService.getPizzaById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ResponseEntity<PizzaDto> responseExpected = pizzaController.getPizzaById(id);
		});
	}

	@Test
	public void testUpdatePizzaNotFound() throws ParseException, ResourceNotFoundException {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		when(pizzaService.getPizzaById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ResponseEntity<PizzaDto> responseExpected = pizzaController.updatePizza(id, mockPizzaDto);
		});
	}

	private PizzaDto createMockPizzaDto() {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		PizzaDto mockPizzaDto = new PizzaDto();
		mockPizzaDto.setId(id);
		mockPizzaDto.setName("Tikka");
		mockPizzaDto.setPrice(25);
		mockPizzaDto.setDescription("Spicy Pizza");
		return mockPizzaDto;
	}

}
