package com.nsia.cobus.domain.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChangeUserInfoModel {

    @Email
    private String email;
    private String adresse;
    private String photoUrl;
    private String phoneNumber;
    private String postalAdress;
    private String prefession;
    private String nationality;
    private String statusMatrimonial;
    private String city;
    @NotNull
    @NotBlank
    private String login;

}
