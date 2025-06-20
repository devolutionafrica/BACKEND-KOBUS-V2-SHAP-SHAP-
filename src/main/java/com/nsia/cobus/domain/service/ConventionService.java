package com.nsia.cobus.domain.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.port.ConventionRepositoryPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConventionService {
    private final ConventionRepositoryPort conventionRepositoryPort;

    public Map<String, ?> fetchAllConventionByCorporate(String username) {
        return conventionRepositoryPort.getAllConventions(username);
    }

    public Map<String, Object> fetchAllContratForConvention(String username, int conventionId) {
        return conventionRepositoryPort.getAllContratForConvention(username, conventionId);
    }
}
