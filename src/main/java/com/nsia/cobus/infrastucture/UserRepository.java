package com.nsia.cobus.infrastucture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.nio.DataSource;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.models.UserLoginAndPassword;
import com.nsia.cobus.domain.port.UserRepositoryPort;
import com.nsia.cobus.infrastucture.rowmapper.UserRowMapper;

@Repository
public class UserRepository implements UserRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User saveUser(User user) {

        throw new UnsupportedOperationException("Unimplemented method 'saveUser'");
    }

    @Override
    public User login(UserLoginAndPassword user) {

        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public List<User> getAllUsers() {

        String req = "SELECT * FROM UTILISATEUR";

        return jdbcTemplate.query(req, new UserRowMapper());

    }

    @Override
    public User findUserByUsername(String username) {
        String req = "SELECT * FROM UTILISATEUR WHERE Login = ?";
        return jdbcTemplate.queryForObject(req, new Object[] { username }, new UserRowMapper());

    }

    @Override
    public Object getProfilinfoByUsername(String username) {

        try {
            String req = """
                    SELECT C.*, U.LOGIN
                    FROM CLIENT_UNIQUE C inner join UTILISATEUR U on U.IDE_CLIENT_UNIQUE=C.IDE_CLIENT_UNIQUE
                    where LOGIN = ?
                    """;
            String pourcentageReq = """
                    SELECT * FROM FnPourcentageProfil(?)
                    """;

            Object user = jdbcTemplate.queryForObject(req, new Object[] { username }, new ColumnMapRowMapper());
            Map pourcentage = (Map) jdbcTemplate.queryForObject(pourcentageReq, new Object[] {
                    username }, new ColumnMapRowMapper());
            Map<String, Object> result = new HashMap<>();
            result.put("user", user);
            result.put("complete", pourcentage.get("POURCENTAGE_PROFIL"));
            return result;
        } catch (Exception e) {
            return "L'utilisateur n'existe pas";
        }

    }

    @Override
    public void firstConnexion(String username, String password, String newPassword, String phone, String email,
            String login) {

        String reqPassword = """
                UPDATE Utilisateur
                 SET MOT_DE_PASSE = @NewPassword, ISFIRSTCONNEXION=1
                 WHERE LOGIN = @Login
                """;

        jdbcTemplate.update(reqPassword, newPassword, login);

        String reqIdClient = """
                select u.ide_client_unique
                from utilisateur u
                where u.login = @Login
                """;

        Map<String, Object> ide_Client_unique = jdbcTemplate.queryForMap(reqIdClient, login, new ColumnMapRowMapper());
        Integer idClientUnique = (Integer) ide_Client_unique.get("ide_client_unique");
        String req = """
                BEGIN TRY
                    BEGIN TRANSACTION;

                    -- Première mise à jour
                    UPDATE Utilisateur
                    SET EMAIL = ?,
                        Mobile = ?
                    WHERE LOGIN = ?;

                    -- Deuxième mise à jour
                    UPDATE CLIENT_UNIQUE
                    SET TELEPHONE = ?
                    WHERE IDE_CLIENT_UNIQUE = ?;

                    -- Si tout se passe bien, validez la transaction
                    COMMIT TRANSACTION;
                END TRY
                    BEGIN CATCH
                        -- Si une erreur survient, annulez la transaction
                        IF @@TRANCOUNT > 0
                            ROLLBACK TRANSACTION;

                        -- Vous pouvez loguer l'erreur ou la remonter si nécessaire
                        -- Exemple pour remonter l'erreur à l'application
                        DECLARE @ErrorMessage NVARCHAR(MAX), @ErrorSeverity INT, @ErrorState INT;
                        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE();

                    RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState);
                END CATCH;
                """;

        jdbcTemplate.update(req, email, phone, login, phone, idClientUnique);
    }

}
