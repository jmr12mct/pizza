/**
 * 
 */
package com.amex.pizza.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mano Ranjan Jayamaran
 *
 */

@Configuration
public class AppConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
