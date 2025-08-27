package com.nsia.cobus.domain;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.FillialeModel;
import com.nsia.cobus.domain.service.ParametreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadFilialeByCode {

    private final ParametreService parametreService;

    public FillialeModel doRead(String code) {
        return parametreService.getFilialeByCode(code);
    }

}
