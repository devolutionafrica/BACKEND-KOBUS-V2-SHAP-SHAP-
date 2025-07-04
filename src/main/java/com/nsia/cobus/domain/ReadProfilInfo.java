package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.infrastucture.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadProfilInfo {

    private final UserRepository userRepository;

    public Object readProfilInfo(String username) {
        return userRepository.getProfilinfoByUsername(username);
    }

}
