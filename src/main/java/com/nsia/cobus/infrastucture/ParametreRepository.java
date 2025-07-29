package com.nsia.cobus.infrastucture;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.FillialeModel;
import com.nsia.cobus.domain.port.ParametreRepositoryPort;
import com.nsia.cobus.infrastucture.rowmapper.FillialeRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ParametreRepository implements ParametreRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FillialeModel> getAllFilliales() {
        String sql = "SELECT * FROM filliale";
        return jdbcTemplate.query(sql, new FillialeRowMapper());
    }

    @Override
    public FillialeModel getFillialeById(String codeFilliale) {
        String req = """
                SELECT * FROM FILLIALE WHERE CodeFiliale = ?
                """;
        return jdbcTemplate.queryForObject(req, new FillialeRowMapper(), codeFilliale);
    }

}
