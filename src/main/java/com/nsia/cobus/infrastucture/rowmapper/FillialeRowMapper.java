package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.FillialeModel;

public class FillialeRowMapper implements RowMapper<FillialeModel> {

    @Override
    public FillialeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        FillialeModel filliale = new FillialeModel();
        filliale.setId(rs.getLong("IDFILIALE"));
        filliale.setCodeFiliale(rs.getString("CODEFILIALE"));
        filliale.setApiUrl(rs.getString("APIURL"));
        filliale.setLibelle(rs.getString("LIBELLE"));
        filliale.setCurrency(rs.getString("devise"));
        filliale.setCallCenterFilliale(rs.getString("CALLCENTERFILIALE"));
        filliale.setMailFilliale(rs.getString("MAILFILIALE"));
        filliale.setCodeFillialePaiement(rs.getString("CODEFILIALEPAIEMENT"));
        filliale.setMobilePagePied(rs.getString("MobilePiedPage"));
        filliale.setUrlVideoPublicitaire(rs.getString("URLVIDEOPUBLICITAIRE"));

        return filliale;
    }

}
