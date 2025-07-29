package com.nsia.cobus.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.FillialeModel;
import com.nsia.cobus.domain.port.ParametreRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParametreService {

    private final ParametreRepositoryPort parametreRepository;

    public List<FillialeModel> getAllFilliales() {
        return parametreRepository.getAllFilliales();
    }

    public FillialeModel getFilialeByCode(String code) {
        return parametreRepository.getFillialeById(code);
    }

}
