package com.nsia.cobus.infrastucture.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nsia.cobus.domain.models.ClientModel;

public class ClientRowMapper implements RowMapper<ClientModel> {

    @Override
    public ClientModel mapRow(ResultSet arg0, int arg1) throws SQLException {
        ClientModel user = new ClientModel();
        user.setLogin(arg0.getString("Login"));
        user.setClientName(arg0.getString("Nom_Client"));
        user.setClientFirstName(arg0.getString("Prenoms_Client"));
        user.setAccountNumber(arg0.getString("Numero_DE_Compte"));
        user.setAgencyCode(arg0.getString("Code_Agence"));
        user.setBankCode(arg0.getString("Code_Banque"));
        user.setRibKey(arg0.getString("Cle_Rib"));

        user.setEmail(arg0.getString("EMAIL"));
        user.setPhoneNumber(arg0.getString("TELEPHONE"));
        user.setPhoneNumber1(arg0.getString("telephone_1"));
        user.setProfession(arg0.getString("Profession"));
        user.setCreationDate(arg0.getString("Date_Debut"));
        user.setCivility(arg0.getString("civilite"));
        user.setClientType(arg0.getString("Type_Client"));
        user.setPlaceOfBirth(arg0.getString("Lieu_Naissance"));
        user.setBirthDate(arg0.getString("Date_Naissance"));
        user.setGender(arg0.getString("Sexe"));
        user.setNationality(arg0.getString("Nationalite"));
        user.setUniqueClientIds(arg0.getString("ide_client_unique"));
        return user;
    }

}
