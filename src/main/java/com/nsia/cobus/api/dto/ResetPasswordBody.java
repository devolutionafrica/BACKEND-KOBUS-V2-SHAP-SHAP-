package com.nsia.cobus.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResetPasswordBody {
    public String username;
    public String resetCode;
    public String password;

}