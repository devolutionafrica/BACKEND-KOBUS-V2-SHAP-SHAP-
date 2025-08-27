package com.nsia.cobus.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.Agency;
import com.nsia.cobus.domain.port.AgenceRepositoryPort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AgenceService {

    private final AgenceRepositoryPort agenceRepositoryPort;

    public List<Agency> getAllAgence() {
        return agenceRepositoryPort.getAllAgence();
    }

}
