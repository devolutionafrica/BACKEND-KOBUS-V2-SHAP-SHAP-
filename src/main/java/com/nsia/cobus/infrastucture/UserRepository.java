package com.nsia.cobus.infrastucture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.nio.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.models.ChangeUserInfoModel;
import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.models.UserLoginAndPassword;
import com.nsia.cobus.domain.port.UserRepositoryPort;
import com.nsia.cobus.infrastucture.rowmapper.ChangeUserInfoRowMapper;
import com.nsia.cobus.infrastucture.rowmapper.UserRowMapper;

import jakarta.validation.Valid;

@Repository
public class UserRepository implements UserRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ContratRepository contratRepository;

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
                 SET MOT_DE_PASSE = ?, ISFIRSTCONNEXION=1
                 WHERE LOGIN = ?
                """;

        jdbcTemplate.update(reqPassword, newPassword, login);

        String reqIdClient = """
                select u.ide_client_unique
                FROM utilisateur u
                WHERE u.login = ?
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

    @Override
    public String updatePassword(@Valid ChangePasswordModel changePasswordModel) {
        try {
            String reqSearchUser = "SELECT IDE_CLIENT_UNIQUE FROM UTILISATEUR WHERE LOGIN= ? AND MOT_DE_PASSE= ?";
            String idClient = jdbcTemplate.queryForObject(reqSearchUser, String.class, changePasswordModel.getLogin(),
                    changePasswordModel.getPassword());
            if (idClient == null) {
                return "Les données sont incorrectes";
            }
            String updateReq = """
                    UPDATE UTILISATEUR
                    SET MOT_DE_PASSE=?
                    WHERE LOGIN=? AND MOT_DE_PASSE=?
                    """;
            jdbcTemplate.update(updateReq, changePasswordModel.getNewPassword(), changePasswordModel.getLogin(),
                    changePasswordModel.getPassword());
            return "Mot de passe bien modifié";
        } catch (Exception e) {
            return "Erreur de modification du mot de passe";
        }
    }

    @Override
    public String updateUserInfo(@Valid ChangeUserInfoModel changeUserInfoModel) {
        try {
            String reqUniqueClient = "SELECT IDE_CLIENT_UNIQUE FROM UTILISATEUR WHERE LOGIN=?";
            String ideClient = jdbcTemplate.queryForObject(reqUniqueClient, String.class,
                    new Object[] { changeUserInfoModel.getLogin() });
            if (ideClient == null) {
                return "Aucun compte associé au username";
            }
            if (changeUserInfoModel.getEmail() != null && changeUserInfoModel.getEmail().equals("") == false) {
                String req = """
                        UPDATE UTILISATEUR
                        SET Email=?
                        WHERE IDE_CLIENT_UNIQUE=?
                        """;
                jdbcTemplate.update(req, changeUserInfoModel.getEmail(), ideClient);
            }

            String req = """
                    SELECT * FROM CLIENT_UNIQUE
                    FROM IDE_CLIENT_UNIQUE=?
                    """;

            try {
                ChangeUserInfoModel user = jdbcTemplate.queryForObject(req, new ChangeUserInfoRowMapper(), ideClient);
                String updateUserReq = """
                        UPDATE CLIENT_UNIQUE
                        SET PROFESSION=?,NATIONALITE=?,ADRESSE_POSTALE=?,TELEPHONE=?,SITUATION_MATRIMONIALE=?,LIEU_HABITATION=?,
                        """;
                String profession = changeUserInfoModel.getPrefession() != null ? changeUserInfoModel.getPrefession()
                        : user.getPrefession();
                String city = changeUserInfoModel.getCity() != null ? changeUserInfoModel.getCity() : user.getCity();
                String phone = changeUserInfoModel.getPhoneNumber() != null ? changeUserInfoModel.getPhoneNumber()
                        : user.getPhoneNumber();
                String adressPostal = changeUserInfoModel.getPostalAdress() != null
                        ? changeUserInfoModel.getPostalAdress()
                        : user.getPostalAdress();
                String nationality = changeUserInfoModel.getNationality() != null ? changeUserInfoModel.getNationality()
                        : user.getNationality();
                String sm = changeUserInfoModel.getStatusMatrimonial() != null
                        ? changeUserInfoModel.getStatusMatrimonial()
                        : user.getStatusMatrimonial();
                jdbcTemplate.update(updateUserReq, profession, nationality, adressPostal, phone, sm, city);

            } catch (Exception e) {
                return "Email mis à jour";
            }

            return "Profil mis à jour ";

        } catch (Exception e) {
            return "Erreur lors de la modification des info personnelles";
        }
    }

    @Override
    public double getEngagement(String username) {

        // recuperation de tous les contrats de l'utilisateur

        String req = """
                SELECT COUNT(*) 'NOMBRE', S.ETAT_QUITTANCE FROM SummaryQuittanceSoldeesImpayees S INNER JOIN CONTRATS C ON (S.NUMERO_POLICE=C.NUMERO_POLICE) INNER JOIN UTILISATEUR U ON (U.IDE_CLIENT_UNIQUE=C.IDE_CLIENT_UNIQUE)
                WHERE U.LOGIN=?
                GROUP BY S.ETAT_QUITTANCE
                            """;

        List<Map<String, Object>> data = jdbcTemplate.queryForList(req, username);

        System.out.println("Taux d'engagement: \n\n" + data + "Total:" + calculTotal(data));
        int total = calculTotal(data);
        int nbSolde = nombreQuittanceSolde(data);
        if (total > 0) {
            return nbSolde / total;
        }
        return 0;

    }

    private int calculTotal(List<Map<String, Object>> data) {
        int count = 0;
        for (Map ele : data) {
            count += (int) ele.get("NOMBRE");
        }
        return count;
    }

    private int nombreQuittanceSolde(List<Map<String, Object>> data) {
        for (Map<String, Object> item : data) {
            if (item.get("ETAT_QUITTANCE").equals("Soldée")) {
                return (int) item.get("NOMBRE");
            }
        }
        return 0;
    }

}
