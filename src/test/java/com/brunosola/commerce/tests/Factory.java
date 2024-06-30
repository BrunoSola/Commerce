package com.brunosola.commerce.tests;

import com.brunosola.commerce.dto.ProductDTO;
import com.brunosola.commerce.entities.Category;
import com.brunosola.commerce.entities.Product;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(null, "new product", "Good product", 100.0, "https://img.com/img.png");
        product.getCategories().add(new Category(1L, "Livros"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product);
    }
}
