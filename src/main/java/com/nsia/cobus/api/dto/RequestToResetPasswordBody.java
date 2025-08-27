package com.nsia.cobus.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RequestToResetPasswordBody {

    private String username;
    private String email;

}
