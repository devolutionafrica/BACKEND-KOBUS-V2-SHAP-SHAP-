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

}
