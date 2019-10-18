package com.amex.pizza.service;

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
 * @author Mano Ranjan Jayamaran
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
		List<PizzaDto> responseExpected = pizzaService.getPizzas();
		assertNotNull(responseExpected);
		assertEquals(1, responseExpected.size());
		assertEquals("Tikka", responseExpected.get(0).getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.get(0).getId().toString());
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

		PizzaDto responseExpected = pizzaService.addPizza(mockPizzaDto);
		assertNotNull(responseExpected);
		assertEquals("Tikka", responseExpected.getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.getId().toString());
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

		PizzaDto responseExpected = pizzaService.updatePizza(mockPizzaDto);
		assertNotNull(responseExpected);
		assertEquals("Tikka", responseExpected.getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.getId().toString());
	}

	/**
	 * Mock and test the GetPizzaById method and convert from entity to dto objects
	 */
	@Test
	public void testGetPizzaById() throws ResourceNotFoundException {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		when(pizzaRepository.findById(any())).thenReturn(Optional.of(mockPizza));
		when(modelMapper.map(any(), any())).thenReturn(mockPizzaDto);
		PizzaDto responseExpected = pizzaService.getPizzaById(id);
		assertNotNull(responseExpected);
		assertEquals("Tikka", responseExpected.getName());
		assertEquals("2e1322aa-67e5-481d-942d-fa18c5989e45", responseExpected.getId().toString());
	}

	/**
	 * Method to create test PizzaDto object
	 */
	private PizzaDto createMockPizzaDto() {
		UUID id = UUID.fromString("2e1322aa-67e5-481d-942d-fa18c5989e45");
		PizzaDto mockPizzaDto = new PizzaDto();
		mockPizzaDto.setId(id);
		mockPizzaDto.setName("Tikka");
		mockPizzaDto.setPrice(25);
		mockPizzaDto.setDescription("Spicy Pizza");
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
