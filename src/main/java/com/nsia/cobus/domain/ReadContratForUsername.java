package com.nsia.cobus.domain;

import java.util.List;

import com.nsia.cobus.domain.service.ContratService;

public record ReadContratForUsername(
        ContratService contratService) {

    public List<?> allContracts(String username) {
        return contratService.getAllContracts(username);
    }

}
