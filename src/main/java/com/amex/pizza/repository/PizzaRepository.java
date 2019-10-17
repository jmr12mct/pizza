/**
 * 
 */
package com.amex.pizza.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amex.pizza.repository.domain.Pizza;

/**
 * @author Mano Ranjan Jayamaran
 *
 */
@Repository
public interface PizzaRepository extends JpaRepository<Pizza, UUID> {

}
