package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.FillialeModel;

public class FillialeRowMapper implements RowMapper<FillialeModel> {

    @Override
    public FillialeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        FillialeModel filliale = new FillialeModel();
        filliale.setId(rs.getLong("ID"));
        filliale.setCodeFiliale(rs.getString("CODE_FILIALE"));
        filliale.setLibelle(rs.getString("LIBELLE"));
        filliale.setCurrency(rs.getString("CURRENCY"));
        filliale.setCallCenterFilliale(rs.getString("CALL_CENTER_FILLIALE"));
        filliale.setMailFilliale(rs.getString("MAIL_FILLIALE"));
        filliale.setCodeFillialePaiement(rs.getString("CODE_FILLIALE_PAIEMENT"));
        filliale.setMobilePagePied(rs.getString("MOBILE_PAGE_PIED"));
        filliale.setUrlVideoPublicitaire(rs.getString("URL_VIDEO_PUBLICITAIRE"));

        return filliale;
    }

}
