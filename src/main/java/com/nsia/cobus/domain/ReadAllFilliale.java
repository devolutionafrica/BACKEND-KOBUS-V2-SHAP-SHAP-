package com.nsia.cobus.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.FillialeModel;
import com.nsia.cobus.domain.service.ParametreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadAllFilliale {
    private final ParametreService parametreService;

    public List<FillialeModel> doRead() {
        return parametreService.getAllFilliales();
    }
}
