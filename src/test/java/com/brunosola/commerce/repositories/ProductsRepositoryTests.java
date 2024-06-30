package com.brunosola.commerce.repositories;

import com.brunosola.commerce.entities.Product;
import com.brunosola.commerce.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductsRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;
    private Product product;
    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
        product = Factory.createProduct();
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExist(){
        Optional<Product> product = productRepository.findById(existingId);
        Assertions.assertTrue(product.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyObjectWhenIdNotExist(){
        Optional<Product> product = productRepository.findById(nonExistingId);
        Assertions.assertTrue(product.isEmpty());
    }

    @Test
    public void insertShouldInsertObjectWhenValidData(){
        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){
        product.setId(null);

        product = productRepository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }
}
