package com.nsia.cobus.infrastucture;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.Agency;
import com.nsia.cobus.domain.port.AgenceRepositoryPort;
import com.nsia.cobus.infrastucture.rowmapper.AgencyRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AgencyRepository implements AgenceRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Agency> getAllAgence() {
        String req = """
                SELECT * FROM AGENCES
                """;
        return jdbcTemplate.query(req, new AgencyRowMapper());
    }

}
