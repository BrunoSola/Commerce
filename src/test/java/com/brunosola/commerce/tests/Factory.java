package com.brunosola.commerce.tests;

import com.brunosola.commerce.dto.CategoryDTO;
import com.brunosola.commerce.dto.ProductDTO;
import com.brunosola.commerce.entities.Category;
import com.brunosola.commerce.entities.Product;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,
                "new product",
                "Good product",
                100.0,
                "https://img.com/img.png");
        product.getCategories().add(new Category(1L, "Livros"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = new Product();
        product.getCategories().add(new Category(1L, "Livros"));
        return new ProductDTO(product);
    }

    public static ProductDTO updateProduct(){
        Product product = new Product(1L,
                "update product",
                "Good product",
                50.0,
                "https://img.com/img2.png");
        product.getCategories().add(new Category(4L, "Eletrônicos"));
        return new ProductDTO(product);
    }

    public static Category createCategory(){
        return new Category(1L, "Livros");
    }

    public static CategoryDTO createCategoryDTO(){
        return new CategoryDTO();
    }
}
