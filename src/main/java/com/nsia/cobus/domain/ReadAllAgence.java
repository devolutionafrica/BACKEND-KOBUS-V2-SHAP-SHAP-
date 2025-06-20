package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.Agency;
import com.nsia.cobus.domain.service.AgenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadAllAgence {

    private final AgenceService agenceService;

    public List<Agency> readAllAgence() {
        return agenceService.getAllAgence();
    }

}
