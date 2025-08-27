package com.nsia.cobus.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.Product;
import com.nsia.cobus.domain.models.ProductCategory;

import com.nsia.cobus.domain.port.ProduitRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProduitRepositoryPort produitRepositoryPort;

    public List<Product> fetchAllProduct() {
        return produitRepositoryPort.fetchAllProduit();
    }

    public List<ProductCategory> fetchAllProducCategory() {
        return produitRepositoryPort.fetchAllProductCategory();
    }

    public List<Product> fetchProductByCategory(String category) {
        return produitRepositoryPort.fetchProductByCategory(category);
    }

    public List<Product> fetchProductByKeyword(String keyword) {
        return produitRepositoryPort.searProductByKeyword(keyword);
    }

}
