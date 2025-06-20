package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.service.UserService;

@Service
public record LoadAllUser(
        UserService userService) {

    public List<User> doAllUser() {
        return userService.getUsers();
    }

}
