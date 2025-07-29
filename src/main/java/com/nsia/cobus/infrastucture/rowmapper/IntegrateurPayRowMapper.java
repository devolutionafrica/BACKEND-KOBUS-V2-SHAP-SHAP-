package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.IntegrateurPayModel;

public class IntegrateurPayRowMapper implements RowMapper<IntegrateurPayModel> {

    @Override
    public IntegrateurPayModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        IntegrateurPayModel integrateurPayModel = new IntegrateurPayModel();
        integrateurPayModel.setDesription(rs.getString("description"));
        integrateurPayModel.setLogo(rs.getString("Logo"));
        integrateurPayModel.setIsActif(rs.getInt("is_actif"));
        integrateurPayModel.setLibelle(rs.getString("libelle"));
        integrateurPayModel.setUrlApi(rs.getString("url_api"));
        integrateurPayModel.setUrlApiRetour(rs.getString("url_api_retour"));
        integrateurPayModel.setId(rs.getString("ide_operateur"));
        return integrateurPayModel;
    }

}
