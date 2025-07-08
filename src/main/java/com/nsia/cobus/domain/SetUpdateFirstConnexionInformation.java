package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SetUpdateFirstConnexionInformation {

    private final UserService userService;

    public void setUpdateFirstConnexionInformation(
            String username, String newPassword, String password, String email, String phone, String login) {

        userService.updateSetFirstConnexion(username, newPassword, password, email, phone, login);

    }

}
