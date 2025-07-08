package com.nsia.cobus.infrastucture;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.Convention;
import com.nsia.cobus.domain.port.ConventionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConventionRepository implements ConventionRepositoryPort {

    @Qualifier("primaryJdbcTemplate")
    private final JdbcTemplate jdbcTemplate;
    @Qualifier("secondaryJdbcTemplate")
    private final JdbcTemplate jdbcTemplate2;

    @Override
    public Map<String, ?> getAllConventions(String username) {

        String query = """
                SELECT C.* FROM CONVENTION C inner join UTILISATEUR U on (C.IDE_CLIENT_UNIQUE=U.IDE_CLIENT_UNIQUE)
                where LOGIN= ?
                """;

        String numberContrat = """
                SELECT COUNT(NUMERO_CONVENTION) totalContrat
                FROM CONTRATS
                WHERE NUMERO_CONVENTION IN
                (
                    SELECT NUMERO_DE_CONVENTION
                    FROM CONVENTION C INNER JOIN UTILISATEUR U ON U.IDE_CLIENT_UNIQUE=C.IDE_CLIENT_UNIQUE
                    WHERE U.LOGIN=?

                )
                """;

        List<Convention> data = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Convention.class), username);

        int totalContrat = jdbcTemplate.queryForObject(numberContrat, new Object[] { username }, Integer.class);

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("totalContrat", totalContrat);

        return response;

    }

    @Override
    public Map<String, Object> getAllContratForConvention(String username, int conventionId) {

        String procedureName = "Ps_ListeContratConvention";

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName)
                .declareParameters(
                        new SqlParameter("NumeroConvention", Types.BIGINT))
                .returningResultSet("data", new ColumnMapRowMapper())
                .withoutProcedureColumnMetaDataAccess();

        Map<String, Object> params = new HashMap<>();

        params.put("NumeroConvention", conventionId);

        return (Map<String, Object>) simpleJdbcCall.execute(params);

    }

    @Override
    public Object getAvisSituationForConvention(String username, String convention, String annee) {
        String processName = "Reporting.Avis_Situation_Police_CU_GROUP";

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate2).withProcedureName(processName)
                .declareParameters(
                        new SqlParameter("CONVENTION", Types.VARCHAR),
                        new SqlParameter("CODE_FILIALE", Types.VARCHAR),
                        new SqlParameter("ANNEE", Types.VARCHAR))
                .withoutProcedureColumnMetaDataAccess()
                .returningResultSet("data", new ColumnMapRowMapper()

                );

        Map<String, Object> params = new HashMap<>();
        params.put("CONVENTION", convention);
        params.put("CODE_FILIALE", "CM_VIE");
        params.put("ANNEE", annee);

        return simpleJdbcCall.execute(params);
    }

}
