package com.brunosola.commerce.controllers;

import com.brunosola.commerce.dto.ProductDTO;
import com.brunosola.commerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id){
        return service.findById(id);

        /* --- Passo a Passo ---
        ProductDTO dto = service.findById(id);
        return dto;
        */
    }
}
