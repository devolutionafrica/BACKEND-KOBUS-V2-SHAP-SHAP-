package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.ChangeUserInfoModel;
import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserInfo {

    private final UserService userService;

    public String doUpdateUserinfo(ChangeUserInfoModel user) {
        return userService.updateChangeUserInfo(user);
    }

}
