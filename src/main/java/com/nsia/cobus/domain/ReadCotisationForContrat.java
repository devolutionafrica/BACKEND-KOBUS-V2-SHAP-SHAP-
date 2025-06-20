package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ContratService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadCotisationForContrat {

    private final ContratService contratService;

    public Object readCotisationForContrat(String idContrat, String dateDeb, String dateFin) {
        return contratService.getCotisationForContrat(idContrat, dateDeb, dateFin);
    }

}
