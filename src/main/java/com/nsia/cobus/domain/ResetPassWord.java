package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetPassWord {

    private final UserService userService;

    public String resetPassword(String username, String newPassword, String code) throws Exception {
        return userService.resetPassword(username, newPassword, code);
    }

}
