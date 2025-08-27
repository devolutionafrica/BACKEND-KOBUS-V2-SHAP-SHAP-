package com.nsia.cobus.api.controllers.agence;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.domain.ReadAllAgence;
import com.nsia.cobus.domain.models.Agency;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kobus/agence")
public class AgencyController {

    private final ReadAllAgence readAllAgence;

    @GetMapping
    public ResponseEntity<List<Agency>> findAllAgence() {
        return ResponseEntity.ok(readAllAgence.readAllAgence());
    }

}
