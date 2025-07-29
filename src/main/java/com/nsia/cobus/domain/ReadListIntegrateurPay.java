package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.IntegrateurPayModel;
import com.nsia.cobus.domain.service.IntegrateurPayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadListIntegrateurPay {

    private final IntegrateurPayService integrateurPayService;

    public List<IntegrateurPayModel> doReadListeIntegrateurPay() {
        return integrateurPayService.getlisteIntegrateur();
    }

}
