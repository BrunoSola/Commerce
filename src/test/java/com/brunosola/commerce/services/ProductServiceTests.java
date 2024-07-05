package com.brunosola.commerce.services;


import com.brunosola.commerce.dto.ProductDTO;
import com.brunosola.commerce.dto.ProductMinDTO;
import com.brunosola.commerce.entities.Product;
import com.brunosola.commerce.repositories.ProductRepository;
import com.brunosola.commerce.services.exceptions.DatabaseException;
import com.brunosola.commerce.services.exceptions.ResourceNotFoundException;
import com.brunosola.commerce.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private String text;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        text = "";

        doNothing().when(productRepository).deleteById(existingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
        doThrow(ResourceNotFoundException.class).when(productRepository).findById(nonExistingId);

        when(productRepository.existsById(existingId)).thenReturn(true);
        when(productRepository.existsById(nonExistingId)).thenReturn(false);
        when(productRepository.existsById(dependentId)).thenReturn(true);
        when(productRepository.searchByName(anyString(),(Pageable) any())).thenReturn(page);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistingId(){
        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                productService.findById(nonExistingId));
        verify(productRepository).findById(nonExistingId);
    }

    @Test
    public void findByIdShouldReturnProductWhenExistingId(){
        ProductDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
        verify(productRepository).findById(existingId);
    }

    @Test
    public void findAllShouldReturnPageProducts(){
        Pageable pageable = PageRequest.of(0,10);
        Page<ProductMinDTO> result = productService.findAll(text, pageable);

        Assertions.assertNotNull(result);
        verify(productRepository).searchByName(text, pageable);
    }

    @Test
    public void deleteShouldNotNothingWhenExistingId(){
        Assertions.assertDoesNotThrow(
                () -> productService.delete(existingId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenNonExistingId(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.delete(nonExistingId));
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId(){
        Assertions.assertThrows(DatabaseException.class,
                () -> productService.delete(dependentId));
    }
}
