/**
 * 
 */
package com.amex.pizza.repository.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents Pizza table Id column value is auto-generated UUID.
 * 
 * Lombok will generate Getters and Setters as well as No Argument Constructor
 * 
 * No Argument Constructor is provided to enable ModelMapper to map domain and
 * entity objects
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", columnDefinition = "VARCHAR(255)")
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "description", nullable = true)
	private String description;

}
