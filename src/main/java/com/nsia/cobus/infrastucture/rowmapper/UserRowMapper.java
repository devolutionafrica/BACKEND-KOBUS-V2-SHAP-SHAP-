package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet arg0, int arg1) throws SQLException {
        User user = new User();
        user.setLogin(arg0.getString("Login"));
        user.setPassword(arg0.getString("Mot_de_passe"));
        user.setIsFirstConnection(arg0.getInt("ISFIRSTCONNEXION"));
        user.setUserTypeId(arg0.getString("IDE_TYPE_UTILISATEUR"));
        return user;
    }

}
