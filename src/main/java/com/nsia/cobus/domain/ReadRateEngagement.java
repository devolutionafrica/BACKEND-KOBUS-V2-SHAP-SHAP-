package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadRateEngagement {

    private final UserService userService;

    public double doRead(String username) {
        return userService.getTauxEngagement(username);
    }

}
