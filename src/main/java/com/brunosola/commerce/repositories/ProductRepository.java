package com.brunosola.commerce.repositories;

import com.brunosola.commerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
