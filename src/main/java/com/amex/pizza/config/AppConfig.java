/**
 * 
 */
package com.amex.pizza.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Initiate ModelMapper Bean
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@Configuration
public class AppConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
