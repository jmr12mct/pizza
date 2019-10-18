package com.amex.pizza.service;

import static com.amex.pizza.util.Constants.DESCRIPTION;
import static com.amex.pizza.util.Constants.ID;
import static com.amex.pizza.util.Constants.NAME;
import static com.amex.pizza.util.Constants.PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.amex.pizza.exception.ResourceNotFoundException;
import com.amex.pizza.repository.PizzaRepository;
import com.amex.pizza.repository.domain.Pizza;
import com.amex.pizza.rest.domain.PizzaDto;

/**
 * Class to test all the methods present in Service using Mockito
 * 
 * Here the conversion between DTO and Entity objects and Vice versa are also
 * verified
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 *
 */
@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {

	@Mock
	private PizzaRepository pizzaRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private PizzaService pizzaService;

	private PizzaDto mockPizzaDto;

	private Pizza mockPizza;

	@BeforeEach
	public void init() {
		mockPizzaDto = createMockPizzaDto();
		mockPizza = createMockPizza();
	}

	/**
	 * Mock and test the GetPizzas method and convert from entity to dto objects
	 */
	@Test
	public void testGetPizzas() {
		List<Pizza> result = new ArrayList<>();
		result.add(mockPizza);
		when(pizzaRepository.findAll()).thenReturn(result);
		when(modelMapper.map(any(), any())).thenReturn(mockPizzaDto);
		final List<PizzaDto> responseExpected = pizzaService.getPizzas();
		assertNotNull(responseExpected);
		assertEquals(1, responseExpected.size());
		assertEquals(NAME, responseExpected.get(0).getName());
		assertEquals(ID, responseExpected.get(0).getId().toString());
		assertEquals(PRICE, responseExpected.get(0).getPrice());
		assertEquals(DESCRIPTION, responseExpected.get(0).getDescription());
	}

	/**
	 * Mock and test the AddPizza method and convert from entity to dto objects and
	 * vice versa
	 */
	@Test
	public void testAddPizza() throws ParseException {
		when(pizzaRepository.save(any())).thenReturn(mockPizza);

		// Using Mockito to mock the return value of a method based on the input
		// parameters that is passed during runtime
		when(modelMapper.map(any(), any())).thenAnswer(invocation -> {
			Object argument = invocation.getArguments()[1];
			if (argument.equals(Pizza.class)) {
				return mockPizza;
			} else if (argument.equals(PizzaDto.class)) {
				return mockPizzaDto;
			}
			throw new InvalidUseOfMatchersException(String.format("Argument %s does not match", argument));
		});

		final PizzaDto responseExpected = pizzaService.addPizza(mockPizzaDto);
		assertNotNull(responseExpected);
		assertEquals(NAME, responseExpected.getName());
		assertEquals(ID, responseExpected.getId().toString());
		assertEquals(PRICE, responseExpected.getPrice());
		assertEquals(DESCRIPTION, responseExpected.getDescription());
	}

	/**
	 * Mock and test the Update Pizza method and convert from entity to dto objects
	 * and vice versa
	 */
	@Test
	public void testUpdatePizza() throws ParseException {
		when(pizzaRepository.save(any())).thenReturn(mockPizza);

		// Using Mockito to mock the return value of a method based on the input
		// parameters that is passed during runtime
		when(modelMapper.map(any(), any())).thenAnswer(invocation -> {
			Object argument = invocation.getArguments()[1];
			if (argument.equals(Pizza.class)) {
				return mockPizza;
			} else if (argument.equals(PizzaDto.class)) {
				return mockPizzaDto;
			}
			throw new InvalidUseOfMatchersException(String.format("Argument %s does not match", argument));
		});

		final PizzaDto responseExpected = pizzaService.updatePizza(mockPizzaDto);
		assertNotNull(responseExpected);
		assertEquals(NAME, responseExpected.getName());
		assertEquals(ID, responseExpected.getId().toString());
		assertEquals(PRICE, responseExpected.getPrice());
		assertEquals(DESCRIPTION, responseExpected.getDescription());
	}

	/**
	 * Mock and test the GetPizzaById method and convert from entity to dto objects
	 */
	@Test
	public void testGetPizzaById() throws ResourceNotFoundException {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		when(pizzaRepository.findById(any())).thenReturn(Optional.of(mockPizza));
		when(modelMapper.map(any(), any())).thenReturn(mockPizzaDto);
		final PizzaDto responseExpected = pizzaService.getPizzaById(id);
		assertNotNull(responseExpected);
		assertEquals(NAME, responseExpected.getName());
		assertEquals(ID, responseExpected.getId().toString());
		assertEquals(PRICE, responseExpected.getPrice());
		assertEquals(DESCRIPTION, responseExpected.getDescription());
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

	/**
	 * Method to create test Pizza object
	 */
	private Pizza createMockPizza() {
		UUID id = UUID.fromString("cf341ff6-63db-48e9-ae57-a6473b5dfb33");
		Pizza mockPizza = new Pizza();
		mockPizza.setId(id);
		mockPizza.setName("Barbeque");
		mockPizza.setPrice(20);
		mockPizza.setDescription("Loaded with Barbeque Chicken");
		return mockPizza;
	}
}
