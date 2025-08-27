package com.nsia.cobus.domain.port;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.Product;
import com.nsia.cobus.domain.models.ProductCategory;

@Repository
public interface ProduitRepositoryPort {

    public List<Product> fetchAllProduit();

    public List<ProductCategory> fetchAllProductCategory();

    public List<Product> fetchProductByCategory(String categorie);

    public List<Product> searProductByKeyword(String keyWord);

}
