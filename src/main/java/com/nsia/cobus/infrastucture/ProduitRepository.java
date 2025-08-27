package com.nsia.cobus.infrastucture;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.Product;
import com.nsia.cobus.domain.models.ProductCategory;
import com.nsia.cobus.domain.port.ProduitRepositoryPort;
import com.nsia.cobus.infrastucture.rowmapper.ProductCategoryRowMapper;
import com.nsia.cobus.infrastucture.rowmapper.ProduitRowMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProduitRepository implements ProduitRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> fetchAllProduit() {
        String req = " SELECT * FROM PRODUITS";
        return jdbcTemplate.query(req, new ProduitRowMapper());
    }

    @Override
    public List<ProductCategory> fetchAllProductCategory() {
        String req = "SELECT DISTINCT DESC_NATURE_POLICE,CODE_NATURE_PRODUIT FROM produits";
        return jdbcTemplate.query(req, new ProductCategoryRowMapper());
    }

    @Override
    public List<Product> fetchProductByCategory(String categorie) {
        String req = "SELECT * FROM PRODUITS WHERE CODE_NATURE_PRODUIT = ?";
        return jdbcTemplate.query(req, new ProduitRowMapper(), new Object[] { categorie });
    }

    @Override
    public List<Product> searProductByKeyword(String keyWord) {
        String req = """
                    SELECT * FROM produits
                WHERE DESC_PRODUCTION LIKE '?' OR DESC_NATURE_POLICE LIKE '?' OR CODE_PRODUIT LIKE '?' OR DESC_PRODUIT LIKE '?'
                    """;
        String format = String.format("%%s%", keyWord);
        return jdbcTemplate.query(req, new ProduitRowMapper(), new Object[] { format, format, format, format });
    }

}
