package com.brunosola.commerce.tests;

import com.brunosola.commerce.dto.CategoryDTO;
import com.brunosola.commerce.dto.ProductDTO;
import com.brunosola.commerce.entities.Category;
import com.brunosola.commerce.entities.Product;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "new product", "Good product", 100.0, "https://img.com/img.png");
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        product.getCategories().add(createCategory());
        return new ProductDTO(product);
    }

    public static Category createCategory(){
        return new Category(1L, "Livros");
    }

    public static CategoryDTO createCategoryDTO(){
        return new CategoryDTO(createCategory());
    }
}
