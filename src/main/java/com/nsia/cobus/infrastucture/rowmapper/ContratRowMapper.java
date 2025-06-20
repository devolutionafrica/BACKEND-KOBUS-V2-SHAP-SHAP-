package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.Contract;

public class ContratRowMapper implements RowMapper<Contract> {

    @Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contrat = new Contract();
        contrat.setContractNumber(rs.getString("Numero_police"));
        // contrat.setAnnuityAmount(null);
        contrat.setSubscriberNumber(rs.getString("numero_client"));
        contrat.setInsuredNumber(rs.getString("numero_adherent"));
        contrat.setProductName(rs.getString("Libeele_de_convention"));
        contrat.setPolicyStartDate(rs.getString("date_debut_effet_police"));
        return contrat;
    }

}
