package com.amex.pizza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/pizzas", String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
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
		PizzaDto pizzaDto = new PizzaDto();
		pizzaDto.setName("Tikka");
		pizzaDto.setPrice(25);
		pizzaDto.setDescription("Spicy Pizza");
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/pizzas", pizzaDto,
				PizzaDto.class);

		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertEquals(UUID.class, postResponse.getBody().getId().getClass());
		assertEquals("Tikka", postResponse.getBody().getName());
		assertEquals(25, postResponse.getBody().getPrice());
		assertEquals("Spicy Pizza", postResponse.getBody().getDescription());
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
		PizzaDto pizzaDto = new PizzaDto();
		pizzaDto.setName("Tikka");
		pizzaDto.setPrice(25);
		pizzaDto.setDescription("Spicy Pizza");
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/pizzas", pizzaDto,
				PizzaDto.class);

		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertEquals(UUID.class, postResponse.getBody().getId().getClass());
		assertEquals("Tikka", postResponse.getBody().getName());
		assertEquals(25, postResponse.getBody().getPrice());
		assertEquals("Spicy Pizza", postResponse.getBody().getDescription());

		postResponse = restTemplate.getForEntity(getRootUrl() + "/api/v1/pizzas/" + postResponse.getBody().getId(),
				PizzaDto.class);

		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertEquals(UUID.class, postResponse.getBody().getId().getClass());
		assertEquals("Tikka", postResponse.getBody().getName());
		assertEquals(25, postResponse.getBody().getPrice());
		assertEquals("Spicy Pizza", postResponse.getBody().getDescription());
	}

	/**
	 * Post a PizzaDto and verify by updating the entity using the generated Id
	 * 
	 * Id is a random UUID, so we just check the class type.
	 * 
	 * Assert the output name to check if the updated name value matches
	 * 
	 * Assert price and Description too check if the retrieved values matches with
	 * the posted pizzaDto
	 */
	@Test
	public void testUpdatePizza() {
		PizzaDto pizzaDto = new PizzaDto();
		pizzaDto.setName("Tikka");
		pizzaDto.setPrice(25);
		pizzaDto.setDescription("Spicy Pizza");
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/pizzas", pizzaDto,
				PizzaDto.class);

		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertEquals(UUID.class, postResponse.getBody().getId().getClass());
		assertEquals("Tikka", postResponse.getBody().getName());
		assertEquals(25, postResponse.getBody().getPrice());
		assertEquals("Spicy Pizza", postResponse.getBody().getDescription());

		pizzaDto.setName("TastyTikka");
		HttpEntity<PizzaDto> requestEntity = new HttpEntity<>(pizzaDto);
		ResponseEntity<PizzaDto> putResponse = restTemplate.exchange("/api/v1/pizzas/" + postResponse.getBody().getId(),
				HttpMethod.PUT, requestEntity, PizzaDto.class);

		assertEquals(HttpStatus.OK, putResponse.getStatusCode());

		assertEquals(UUID.class, putResponse.getBody().getId().getClass());
		assertEquals("TastyTikka", putResponse.getBody().getName());
		assertEquals(25, putResponse.getBody().getPrice());
		assertEquals("Spicy Pizza", putResponse.getBody().getDescription());
	}

}
