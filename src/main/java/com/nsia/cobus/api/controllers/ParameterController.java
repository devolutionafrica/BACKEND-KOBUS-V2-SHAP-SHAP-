package com.nsia.cobus.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.domain.ReadAllFilliale;
import com.nsia.cobus.domain.ReadFilialeByCode;
import com.nsia.cobus.domain.models.FillialeModel;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/kobus/parameters")
@RequiredArgsConstructor
public class ParameterController {

    private final ReadAllFilliale readAllFilliale;
    private final ReadFilialeByCode readFilialeByCode;

    @GetMapping("/filiale")
    public ResponseEntity<List<FillialeModel>> findAllFilliale() {
        return ResponseEntity.ok(readAllFilliale.doRead());
    }

    @GetMapping("/filiale/{code}")
    public ResponseEntity<FillialeModel> findFillialeByCode(@PathVariable String code) {
        return ResponseEntity.ok(readFilialeByCode.doRead(code));
    }

}
