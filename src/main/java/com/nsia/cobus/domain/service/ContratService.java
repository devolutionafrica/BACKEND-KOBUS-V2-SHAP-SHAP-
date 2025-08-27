package com.nsia.cobus.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.Contract;
import com.nsia.cobus.domain.port.ContratRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContratService {

    public final ContratRepositoryPort contratRepositoryPort;

    public List<?> getAllContracts(String username) {
        return contratRepositoryPort.getAllContratByUsername(username);
    }

    public Map<String, Object> getContractById(String id, String username) {
        return contratRepositoryPort.getContratById(id, username);
    }

    public Object getListPrestationForContrat(String contratid) {
        return contratRepositoryPort.getSinistreForContrat(contratid, "");
    }

    public Object getCotisationForContrat(String police, String dateDeb, String dateFin) {
        return contratRepositoryPort.getAllCotisationByData(police, dateDeb, dateFin);
    }

    public Object getAvisSituation(String police, String codeFiliale, String date) {
        return contratRepositoryPort.getAvisSituationForContract(police, codeFiliale, date);
    }

}
