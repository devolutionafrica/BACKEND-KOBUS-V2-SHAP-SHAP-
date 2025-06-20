package com.nsia.cobus.domain;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.service.ContratService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadDetailsInformationForContrat {

    private final ContratService contratService;

    public Map<String, Object> readDetailsInformationForContrat(String idContrat, String username) {
        return contratService.getContractById(idContrat, username);
    }

}
