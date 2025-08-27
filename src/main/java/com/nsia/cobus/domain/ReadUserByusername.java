package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadUserByusername {

    private final UserService userService;

    public User doRead(String username) {
        return userService.findUserbyUsername(username);
    }

}
