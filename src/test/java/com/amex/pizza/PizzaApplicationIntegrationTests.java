package com.amex.pizza;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amex.pizza.rest.domain.PizzaDto;

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

	@Test
	public void testGetPizzaById() {
		PizzaDto pizzaDto = restTemplate.getForObject(getRootUrl() + "/pizzas/2e1322aa-67e5-481d-942d-fa18c5989e45",
				PizzaDto.class);
		assertNotNull(pizzaDto);
	}

	@Test
	public void testAddPizza() {
		PizzaDto pizzaDto = new PizzaDto();
		pizzaDto.setName("Tikka");
		pizzaDto.setPrice(25);
		pizzaDto.setDescription("Spicy Pizza");
		ResponseEntity<PizzaDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/pizzas", pizzaDto,
				PizzaDto.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testGetAllPizzas() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/pizzas", HttpMethod.GET, entity,
				String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testUpdatePizza() {
		UUID id = UUID.randomUUID();
		PizzaDto pizzaDto = restTemplate.getForObject(getRootUrl() + "/pizzas/" + id, PizzaDto.class);
		pizzaDto.setName("Barbeque");
		pizzaDto.setPrice(20);
		pizzaDto.setDescription("Loaded with Barbeque Chicken");
		restTemplate.put(getRootUrl() + "/pizzas/" + id, pizzaDto);
		PizzaDto updatedPizzaDto = restTemplate.getForObject(getRootUrl() + "/pizzas/" + id, PizzaDto.class);
		assertNotNull(updatedPizzaDto);
	}

}
