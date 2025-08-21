package com.nsia.cobus.infrastucture;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.nsia.cobus.domain.port.ContratRepositoryPort;

// @RequiredArgsConstructor

public record ContratRepository(
                @Qualifier("primaryJdbcTemplate") JdbcTemplate jdbcTemplate,

                @Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplateSecondary)
                implements ContratRepositoryPort {

        @Override
        public List<Map<String, Object>> getAllContratByUsername(String username) {
                String query = """
                                    Select * From dbo.FnConsultationPoliceDetail(?)
                                """;

                return jdbcTemplate.queryForList(query, username);
        }

        @Override
        public Map<String, Object> getContratById(String contratId, String username) {

                /*
                 * Récupère la liste des contrats pour un utilisateur
                 */
                String req = """
                                    Select C.*
                                    From dbo.FnConsultationPoliceDetailInd(?) C inner join Utilisateur U on (U.ide_client_unique=C.ide_client_unique)
                                    where U.login = ?
                                """;
                Map<String, Object> contrat = jdbcTemplate.queryForMap(req, contratId, username);

                Long userId = (Long) contrat.get("IDE_CLIENT_UNIQUE");

                String req2 = """
                                SELECT C.* FROM CLIENT_UNIQUE C
                                where ide_client_unique=?
                                """;

                Map<String, Object> user = jdbcTemplate.queryForMap(req2, userId);
                Map<String, Object> result = new HashMap<>();
                result.put("data", contrat);
                result.put("suscripber", user);

                return result;
        }

        @Override
        public Object getSinistreForContrat(String contratId, String username) {
                String req = """
                                SELECT * FROM FnConsultationSinistreDetailIndiv
                                (?)
                                """;

                return jdbcTemplate.queryForList(req, contratId);
        }

        @Override
        public Map<String, Object> getAllCotisationByData(String policeId, String dateDeb, String dateFin) {

                String req = """
                                SELECT * FROM FnConsultationCotisationClientIndiv(?, ?, ?)
                                """;
                String summaryReq = """
                                SELECT * FROM FnConsultationCotisationSommeTotalClientIndiv(?, ?, ?)
                                """;
                Object data = jdbcTemplate.queryForList(req, policeId, dateDeb, dateFin);

                /*
                 * Mettre la date au forme yyyyMMdd
                 */
                String convertDateDeb = String.format("%s%s%s", dateDeb.split("-")[0], dateDeb.split("-")[1],
                                dateDeb.split("-")[2]);
                String convertDateFin = String.format("%s%s%s", dateFin.split("-")[0], dateFin.split("-")[1],
                                dateFin.split("-")[2]);
                Object totalCotisation = jdbcTemplate.queryForList(summaryReq, policeId, convertDateDeb,
                                convertDateFin);
                Map<String, Object> result = new HashMap<>();
                result.put("cotisations", data);
                result.put("summary", totalCotisation);
                return result;
        }

        @Override
        public Object getAvisSituationForContract(String codeFiliale, String police, String date) {
                String processName = "Reporting.Avis_Situation_Police_CU";
                SimpleJdbcCall processCaller = new SimpleJdbcCall(jdbcTemplateSecondary).withProcedureName(processName)
                                .declareParameters(
                                                new SqlParameter("ANNEE", Types.VARCHAR),
                                                new SqlParameter("CODE_FILIALE", Types.VARCHAR),
                                                new SqlParameter("POLICE", Types.BIGINT))
                                .returningResultSet("data", new ColumnMapRowMapper())
                                .withoutProcedureColumnMetaDataAccess();

                Map<String, Object> param = new HashMap<>();
                param.put("ANNEE", date);
                param.put("CODE_FILIALE", codeFiliale);
                param.put("POLICE", police);

                return processCaller.execute(param);

        }

}
