package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.ProductCategory;
import com.nsia.cobus.domain.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadAllProductCategory {

    private final ProductService productService;

    public List<ProductCategory> readAllProductCategory() {
        return productService.fetchAllProducCategory();
    }

}
