package com.nsia.cobus.domain;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ContratService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadSinistreForContrat {

    private final ContratService contratService;

    public Object readSinistreForContrat(String idContrat) {
        return contratService.getListPrestationForContrat(idContrat);
    }

}
