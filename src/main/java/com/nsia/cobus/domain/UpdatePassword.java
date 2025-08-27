package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePassword {

    private final UserService userService;

    public String updatePassword(ChangePasswordModel changePasswordModel) {
        return userService.updatePassword(changePasswordModel);
    }

}
