package com.nsia.cobus.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstUserInfo {

    private String email;
    private String phone;
    private String newPassword;
    private String password;
    private String login;

}
