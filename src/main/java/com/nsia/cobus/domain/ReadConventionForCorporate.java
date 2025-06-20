package com.nsia.cobus.domain;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ConventionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReadConventionForCorporate {

    private final ConventionService conventionService;

    public Map<String, ?> doReadConvention(String username) {
        return conventionService.fetchAllConventionByCorporate(username);
    }

}
