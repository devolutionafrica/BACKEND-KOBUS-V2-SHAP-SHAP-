package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.Product;

public class ProduitRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product produit = new Product();
        produit.setCodeFilliale(rs.getString("CODE_FILIALE"));
        produit.setCodeProduction(rs.getString("CODE_PRODUCTION"));
        produit.setName(rs.getString("DESC_NATURE_POLICE"));
        produit.setIdeProduit(rs.getString("IDE_PRODUIT"));
        produit.setFamille(rs.getString("DESC_NATURE_POLICE"));
        return produit;
    }

}
