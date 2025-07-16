package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.ProductCategory;

public class ProductCategoryRowMapper implements RowMapper<ProductCategory> {

    @Override
    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductCategory category = new ProductCategory();

        category.setName("DESC_NATURE_POLICE");
        category.setNature("CODE_NATURE_PRODUIT");
        return category;
    }

}
