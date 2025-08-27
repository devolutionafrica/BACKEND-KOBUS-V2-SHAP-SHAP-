package com.nsia.cobus.infrastucture;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.IntegrateurPayModel;
import com.nsia.cobus.domain.port.IntegrateurPayPort;
import com.nsia.cobus.infrastucture.rowmapper.IntegrateurPayRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class IntegrateurPayRepository implements IntegrateurPayPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<IntegrateurPayModel> allIntegrateurs() {
        String req = "SELECT * FROM INTEGRATEUR_PAIEMENT";

        return jdbcTemplate.query(req, new IntegrateurPayRowMapper());
    }

}
