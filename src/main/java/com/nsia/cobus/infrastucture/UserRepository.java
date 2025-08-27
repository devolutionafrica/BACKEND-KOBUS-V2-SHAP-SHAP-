package com.nsia.cobus.infrastucture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.nio.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.models.ChangeUserInfoModel;
import com.nsia.cobus.domain.models.ClientModel;
import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.models.UserLoginAndPassword;
import com.nsia.cobus.domain.port.UserRepositoryPort;
import com.nsia.cobus.infrastucture.rowmapper.ChangeUserInfoRowMapper;
import com.nsia.cobus.infrastucture.rowmapper.ClientRowMapper;
import com.nsia.cobus.infrastucture.rowmapper.UserRowMapper;
import com.nsia.cobus.service.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    // private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // public UserRepository(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder
    // passwordEncoder, EmailService emailService) {
    // this.jdbcTemplate = jdbcTemplate;
    // // this.passwordEncoder = passwordEncoder;
    // // this.emailService = emailService;
    // }

    @Autowired
    ContratRepository contratRepository;

    @Override
    public User saveUser(User user) {

        throw new UnsupportedOperationException("Unimplemented method 'saveUser'");
    }

    @Override
    public String requestToResetPassword(String username, String email) {
        String generateCode = generatePassword();
        // String passwordEncode = passwordEncoder(generateCode);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = passwordEncoder.encode(generateCode);

        String req = """
                UPDATE UTILISATEUR
                SET CODE_RESET_PASSWORD=?
                WHERE LOGIN=?
                """;

        jdbcTemplate.update(req, passwordEncode, username);
        emailService.envoyerEmail(email, "Demande de changement de mot de passe",
                String.format(
                        "\"Vous avez demandé un changement de mot de passe. Voici votre mot de passe de récupération %s:\"",
                        generateCode));
        return "Demande de changement de mot de passe envoyée";
    }

    @Override
    public List<User> getAllUsers() {

        String req = "SELECT * FROM UTILISATEUR";

        return jdbcTemplate.query(req, new UserRowMapper());

    }

    @Override
    public User findUserByUsername(String username) {
        try {
            String req = "SELECT * FROM UTILISATEUR WHERE Login = ?";
            return jdbcTemplate.queryForObject(req, new Object[] { username }, new UserRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ClientModel getProfilinfoByUsername(String username) {

        String req = """
                SELECT C.*, U.LOGIN
                FROM CLIENT_UNIQUE C inner join UTILISATEUR U on U.IDE_CLIENT_UNIQUE=C.IDE_CLIENT_UNIQUE
                where LOGIN = ?
                """;
        // String pourcentageReq = """
        // SELECT * FROM FnPourcentageProfil(?)
        // """;

        ClientModel client = jdbcTemplate.queryForObject(req, new Object[] { username }, new ClientRowMapper());
        // Map pourcentage = (Map) jdbcTemplate.queryForObject(pourcentageReq, new
        // Object[] {
        // username }, new ColumnMapRowMapper());
        // Map<String, Object> result = new HashMap<>();
        // result.put("user", user);
        // result.put("complete", pourcentage.get("POURCENTAGE_PROFIL"));
        return client;

    }

    @Override
    public Integer getProfilCompletion(String username) {

        String pourcentageReq = """
                SELECT * FROM FnPourcentageProfil(?)
                """;

        Map pourcentage = jdbcTemplate.queryForObject(pourcentageReq, new Object[] {
                username }, new ColumnMapRowMapper());

        return (Integer) pourcentage.get("POURCENTAGE_PROFIL");

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
            String idClient = jdbcTemplate.queryForObject(reqSearchUser, String.class,
                    changePasswordModel.getUsername(),
                    changePasswordModel.getPassword());
            if (idClient == null) {
                return "Les données sont incorrectes";
            }
            if (changePasswordModel.getNewPassword() == null || changePasswordModel.getNewPassword().equals("")) {
                return "Le nouveau mot de passe ne peut pas être vide";
            }
            String updateReq = """
                    UPDATE UTILISATEUR
                    SET MOT_DE_PASSE=?
                    WHERE LOGIN=? AND MOT_DE_PASSE=?
                    """;
            jdbcTemplate.update(updateReq, changePasswordModel.getNewPassword(), changePasswordModel.getUsername(),
                    changePasswordModel.getPassword());
            return "Mot de passe bien modifié";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur de modification du mot de passe";
        }
    }

    @Override
    public String updateUserInfo(ChangeUserInfoModel changeUserInfoModel) {
        try {
            System.out.println("usename:\n" + changeUserInfoModel.getUsername());
            String reqUniqueClient = "SELECT IDE_CLIENT_UNIQUE FROM UTILISATEUR WHERE LOGIN=?";
            String ideClient = jdbcTemplate.queryForObject(reqUniqueClient,
                    new Object[] { changeUserInfoModel.getUsername() }, String.class);

            System.out.println("Premier pas passé \n\n");
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

            try {

                String updateUserReq = """
                        UPDATE CLIENT_UNIQUE
                        SET PROFESSION=ISNULL(?,PROFESSION),NATIONALITE=ISNULL(?,NATIONALITE),ADRESSE_POSTALE=ISNULL(?,ADRESSE_POSTALE),TELEPHONE=ISNULL(?,TELEPHONE),SITUATION_MATRIMONIALE=ISNULL(?,SITUATION_MATRIMONIALE),LIEU_HABITATION=ISNULL(?,LIEU_HABITATION),SEXE=ISNULL(?,SEXE)
                        """;

                jdbcTemplate.update(updateUserReq, changeUserInfoModel.getPrefession(),
                        changeUserInfoModel.getNationality(), changeUserInfoModel.getPostalAdress(),
                        changeUserInfoModel.getPhoneNumber(), changeUserInfoModel.getStatusMatrimonial(),
                        changeUserInfoModel.getCity(), changeUserInfoModel.getGender());

            } catch (Exception e) {
                e.printStackTrace();
                return "Problème interne";
            }

            return "Profil mis à jour ";

        } catch (Exception e) {
            e.printStackTrace();
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

    private String generatePassword() {
        String characters = "0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }

    @Override
    public String resetPassword(String username, String newPassword, String code) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Map<String, ?> userInfo = jdbcTemplate.queryForMap("""
                SELECT CODE_RESET_PASSWORD FROM UTILISATEUR WHERE LOGIN=?
                """, username);
        if (!passwordEncoder.matches(code, userInfo.get("CODE_RESET_PASSWORD").toString())) {
            return "Code de réinitialisation invalide";
        }
        String req = """
                UPDATE UTILISATEUR
                SET MOT_DE_PASSE=?
                WHERE LOGIN=? AND CODE_RESET_PASSWORD=?
                """;
        jdbcTemplate.update(req, newPassword, username, code);
        jdbcTemplate.update("""
                UPDATE UTILISATEUR
                SET CODE_RESET_PASSWORD=NULL
                WHERE LOGIN=?
                """, username);
        // if (updatedRows > 0) {
        // return "Mot de passe réinitialisé avec succès";
        // }
        return "Mot de passe réinitialisé avec succès";
    }

}
