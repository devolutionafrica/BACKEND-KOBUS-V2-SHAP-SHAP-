package com.nsia.cobus.domain.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginAndPassword {

    public String username;
    public String password;

}
