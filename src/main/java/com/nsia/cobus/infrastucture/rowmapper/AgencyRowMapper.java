package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.Agency;

public class AgencyRowMapper implements RowMapper<Agency> {

    @Override
    public Agency mapRow(ResultSet rs, int rowNum) throws SQLException {

        Agency agence = new Agency();

        agence.setNameAgence(rs.getString("DistrictAgence"));
        agence.setLatitude(rs.getLong("Latitude"));
        agence.setLongitude(rs.getLong("longitude"));
        agence.setPhoneAgence(rs.getString("telephoneagence"));
        agence.setLocalisationAgence(rs.getString("localisationagence"));
        return agence;

    }

}
