/**
 * 
 */
package com.amex.pizza.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amex.pizza.repository.domain.Pizza;

/**
 * Repository that interacts with Pizza Table
 * 
 * @author Mano Ranjan Jayamaran
 * @version 1.0
 * @see Pizza
 */
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, UUID> {

}
