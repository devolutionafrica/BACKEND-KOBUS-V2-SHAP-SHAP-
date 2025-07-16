package com.nsia.cobus.domain.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChangePasswordModel {

    @NotNull
    @NotBlank
    private String login;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String newPassword;

}
