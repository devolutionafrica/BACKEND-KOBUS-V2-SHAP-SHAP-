package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestToResetPassword {

    private final UserService userService;

    public String doRead(String username, String email) {
        return userService.requestToChangePassword(username, email);
    }

}
