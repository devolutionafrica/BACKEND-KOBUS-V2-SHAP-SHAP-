package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadProfilCompletion {

    private final UserService userService;

    public Integer doRead(String username) {
        return userService.getTauxCompletionClientInfo(username);
    }

}
