package com.brunosola.commerce.services;


import com.brunosola.commerce.dto.CategoryDTO;
import com.brunosola.commerce.dto.ProductDTO;
import com.brunosola.commerce.dto.ProductMinDTO;
import com.brunosola.commerce.entities.Category;
import com.brunosola.commerce.entities.Product;
import com.brunosola.commerce.repositories.CategoryRepository;
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

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO productDTO;
    private ProductDTO productDTOUpdate;
    private Category category;
    private Category newCategory;
    private CategoryDTO categoryDTO;
    private String text;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        productDTOUpdate = Factory.updateProduct();
        category = Factory.createCategory();
        newCategory = new Category(4L, "Eletrônicos");
        page = new PageImpl<>(List.of(product));
        text = "";

        doNothing().when(productRepository).deleteById(existingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
        doThrow(ResourceNotFoundException.class).when(productRepository).findById(nonExistingId);
        doThrow(ResourceNotFoundException.class).when(productRepository).getReferenceById(nonExistingId);

        when(productRepository.existsById(existingId)).thenReturn(true);
        when(productRepository.existsById(nonExistingId)).thenReturn(false);
        when(productRepository.existsById(dependentId)).thenReturn(true);
        when(productRepository.searchByName(anyString(),(Pageable) any())).thenReturn(page);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.getReferenceById(existingId)).thenReturn(product);

        when(categoryRepository.getReferenceById(1L)).thenReturn(category);
        when(categoryRepository.getReferenceById(4L)).thenReturn(newCategory);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNonExistingId() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.update(nonExistingId, productDTOUpdate));
        verify(productRepository).getReferenceById(nonExistingId);
    }

    @Test
    public void updateShouldUpdateProductWhenExistingId(){
        ProductDTO result = productService.update(existingId, productDTOUpdate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("update product", result.getName());
        Assertions.assertEquals("Good product", result.getDescription());
        Assertions.assertEquals(50.0, result.getPrice());
        Assertions.assertEquals("https://img.com/img2.png", result.getImgUrl());

        Assertions.assertTrue(result.getCategories().stream().anyMatch(x -> x.getId().equals(newCategory.getId())));
        Assertions.assertTrue(product.getCategories().stream().anyMatch(x -> x.getId().equals(newCategory.getId())));

        verify(categoryRepository).getReferenceById(newCategory.getId());
        verify(productRepository).getReferenceById(product.getId());
        verify(productRepository).save(any());
    }

    @Test
    public void insertShouldCreateNewProduct(){
        ProductDTO result = productService.insert(productDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("new product", result.getName());
        Assertions.assertEquals("Good product", result.getDescription());
        Assertions.assertEquals(100.0, result.getPrice());
        Assertions.assertEquals("https://img.com/img.png", result.getImgUrl());

        Assertions.assertTrue(result.getCategories().stream().anyMatch(x -> x.getId().equals(category.getId())));
        Assertions.assertTrue(result.getCategories().stream().anyMatch(x -> x.getName().equals(category.getName())));
        Assertions.assertTrue(product.getCategories().stream().anyMatch(x -> x.getId().equals(category.getId())));
        Assertions.assertTrue(product.getCategories().stream().anyMatch(x -> x.getName().equals(category.getName())));
        verify(productRepository).save(any());
        verify(categoryRepository).getReferenceById(category.getId());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenNonExistingId(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> productService.findById(nonExistingId));
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
