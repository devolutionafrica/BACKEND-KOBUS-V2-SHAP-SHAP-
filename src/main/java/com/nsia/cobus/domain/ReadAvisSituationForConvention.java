package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ConventionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadAvisSituationForConvention {

    private final ConventionService conventionService;

    public Object readAvisSituationForConvention(String username, String conventionId, String annee) {
        return conventionService.fetchAvisSituationForConvention(username, conventionId, annee);
    }

}
