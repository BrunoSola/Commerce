package com.brunosola.commerce.services;


import com.brunosola.commerce.repositories.ProductRepository;
import com.brunosola.commerce.services.exceptions.DatabaseException;
import com.brunosola.commerce.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;

        doNothing().when(productRepository).deleteById(existingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);

        when(productRepository.existsById(existingId)).thenReturn(true);
        when(productRepository.existsById(nonExistingId)).thenReturn(false);
        when(productRepository.existsById(dependentId)).thenReturn(true);
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
