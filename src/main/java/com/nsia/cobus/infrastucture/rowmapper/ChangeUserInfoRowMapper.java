package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.ChangeUserInfoModel;

public class ChangeUserInfoRowMapper implements RowMapper<ChangeUserInfoModel> {

    @Override
    public ChangeUserInfoModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        ChangeUserInfoModel user = new ChangeUserInfoModel();
        user.setAdresse(rs.getString("ADRESSE_POSTALE"));
        user.setPhoneNumber(rs.getString("TELEPHONE"));
        user.setPrefession(rs.getString("PROFESSION"));
        user.setNationality(rs.getString("NATIONALITE"));
        user.setStatusMatrimonial("SITUATION_MATRIMONIALE");
        user.setCity(rs.getString("LIEU_HABITATION"));
        return user;
    }

}
