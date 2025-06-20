package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ContratService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadAvisSituationForContrat {

    private final ContratService contratService;

    public Object readAvisSituation(
            String filiale,
            String police,
            String dates) {
        return contratService.getAvisSituation(filiale, police, dates);
    }

}
