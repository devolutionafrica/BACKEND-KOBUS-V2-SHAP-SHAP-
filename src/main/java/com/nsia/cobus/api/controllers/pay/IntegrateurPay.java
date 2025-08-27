package com.nsia.cobus.api.controllers.pay;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.domain.ReadListIntegrateurPay;
import com.nsia.cobus.domain.models.IntegrateurPayModel;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/kobus/pay")
@RequiredArgsConstructor
public class IntegrateurPay {
    private final ReadListIntegrateurPay readListIntegrateurPay;

    @GetMapping("/liste-integrateur")
    public ResponseEntity<List<IntegrateurPayModel>> findAllIntegrateur() {
        return ResponseEntity.ok(readListIntegrateurPay.doReadListeIntegrateurPay());
    }

}
