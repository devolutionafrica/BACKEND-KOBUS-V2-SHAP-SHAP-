package com.nsia.cobus.domain;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ConventionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReadAllContratByConvention {

    private final ConventionService conventionService;

    public Map<String, Object> readAllContratByConvention(String username, int idConvention) {
        return conventionService.fetchAllContratForConvention(username, idConvention);
    }

}
