package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.Product;
import com.nsia.cobus.domain.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadProductBykeyword {

    private final ProductService productService;

    public List<Product> readProductByKeyword(String keyword) {
        return productService.fetchProductByKeyword(keyword);
    }

}
