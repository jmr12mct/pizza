package com.amex.pizza;

import static com.amex.pizza.util.Constants.DESCRIPTION;
import static com.amex.pizza.util.Constants.NAME;
import static com.amex.pizza.util.Constants.NEW_NAME;
import static com.amex.pizza.util.Constants.PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amex.pizza.rest.domain.PizzaDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration testing class to test the APIs provided by controller
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PizzaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PizzaApplicationIntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
	}

	/**
	 * Get the List as JSON String and assert the List
	 * 
	 * Since id is a random UUID, we just check if the other fields are present and
	 * it is returned as List
	 */
	@Test
	public void testGetAllPizzas() throws IOException, JSONException {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/pizzas", String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(response.getBody());
		assertEquals(OK, response.getStatusCode());
		assertFalse(responseJson.isMissingNode());
		assertTrue(responseJson.toString()
				.contains("\"name\":\"Tikka\",\"price\":25.0,\"description\":\"Spicy Pizza\"}]"));
	}

	/**
	 * Post a PizzaDto and verify if the Id of type UUID as id is a random UUID, we
	 * just check the class type
	 * 
	 * Assert name, price and Description too
	 */
	@Test
	public void testAddPizza() {
		PizzaDto pizzaDto = createMockPizzaDto();
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/pizzas", pizzaDto,
				PizzaDto.class);
		final PizzaDto addedPizzaDto = postResponse.getBody();
		assertEquals(OK, postResponse.getStatusCode());
		assertEquals(UUID.class, addedPizzaDto.getId().getClass());
		assertEquals(NAME, addedPizzaDto.getName());
		assertEquals(PRICE, addedPizzaDto.getPrice());
		assertEquals(DESCRIPTION, addedPizzaDto.getDescription());
	}

	/**
	 * Post a PizzaDto and verify the same entity could be retrieved using the
	 * generated Id
	 * 
	 * Id is a random UUID, so we just check the class type.
	 * 
	 * Assert name, price and Description to check if the retrieved values matches
	 * with the posted pizzaDto
	 */
	@Test
	public void testGetPizzaById() {
		PizzaDto pizzaDto = createMockPizzaDto();
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/pizzas", pizzaDto,
				PizzaDto.class);
		final PizzaDto addedPizzaDto = postResponse.getBody();
		assertEquals(OK, postResponse.getStatusCode());
		assertEquals(UUID.class, addedPizzaDto.getId().getClass());
		assertEquals(NAME, addedPizzaDto.getName());
		assertEquals(PRICE, addedPizzaDto.getPrice());
		assertEquals(DESCRIPTION, addedPizzaDto.getDescription());

		ResponseEntity<PizzaDto> getResponse = restTemplate
				.getForEntity(getRootUrl() + "/api/v1/pizzas/" + addedPizzaDto.getId(), PizzaDto.class);
		final PizzaDto pizzaDtoForId = getResponse.getBody();
		assertEquals(OK, getResponse.getStatusCode());
		assertEquals(UUID.class, pizzaDtoForId.getId().getClass());
		assertEquals(NAME, pizzaDtoForId.getName());
		assertEquals(PRICE, pizzaDtoForId.getPrice());
		assertEquals(DESCRIPTION, pizzaDtoForId.getDescription());
	}

	/**
	 * Post a PizzaDto and verify by updating the entity using the generated Id
	 * 
	 * Id is a random UUID, so we just check the class type.
	 * 
	 * Assert the output name to check if the updated name value matches
	 * 
	 * Assert price and Description to check if the retrieved values matches with
	 * the posted pizzaDto
	 */
	@Test
	public void testUpdatePizza() {
		PizzaDto pizzaDto = createMockPizzaDto();
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/pizzas", pizzaDto,
				PizzaDto.class);
		final PizzaDto addedPizzaDto = postResponse.getBody();
		assertEquals(OK, postResponse.getStatusCode());
		assertEquals(UUID.class, addedPizzaDto.getId().getClass());
		assertEquals(NAME, addedPizzaDto.getName());
		assertEquals(PRICE, addedPizzaDto.getPrice());
		assertEquals(DESCRIPTION, addedPizzaDto.getDescription());

		// Updating the name value
		pizzaDto.setName(NEW_NAME);
		HttpEntity<PizzaDto> requestEntity = new HttpEntity<>(pizzaDto);
		ResponseEntity<PizzaDto> putResponse = restTemplate.exchange(getRootUrl() + "/api/v1/pizzas/" + addedPizzaDto.getId(),
				HttpMethod.PUT, requestEntity, PizzaDto.class);
		final PizzaDto updatedPizzaDto = putResponse.getBody();
		assertEquals(HttpStatus.OK, putResponse.getStatusCode());
		assertEquals(UUID.class, updatedPizzaDto.getId().getClass());
		assertEquals(NEW_NAME, updatedPizzaDto.getName());
		assertEquals(PRICE, updatedPizzaDto.getPrice());
		assertEquals(DESCRIPTION, updatedPizzaDto.getDescription());
	}

	/**
	 * Method to create test PizzaDto object
	 */
	private PizzaDto createMockPizzaDto() {
		PizzaDto mockPizzaDto = new PizzaDto();
		mockPizzaDto.setName(NAME);
		mockPizzaDto.setPrice(PRICE);
		mockPizzaDto.setDescription(DESCRIPTION);
		return mockPizzaDto;
	}

}
