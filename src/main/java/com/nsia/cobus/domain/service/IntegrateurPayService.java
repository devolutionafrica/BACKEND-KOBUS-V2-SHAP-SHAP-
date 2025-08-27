package com.nsia.cobus.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.IntegrateurPayModel;
import com.nsia.cobus.domain.port.IntegrateurPayPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrateurPayService {

    private final IntegrateurPayPort integrateurPayPort;

    public List<IntegrateurPayModel> getlisteIntegrateur() {
        return integrateurPayPort.allIntegrateurs();
    }

}
