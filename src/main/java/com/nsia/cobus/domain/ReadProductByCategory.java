package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.Product;
import com.nsia.cobus.domain.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadProductByCategory {

    private final ProductService productService;

    public List<Product> readProductByCategory(String category) {
        return productService.fetchProductByCategory(category);
    }

}
