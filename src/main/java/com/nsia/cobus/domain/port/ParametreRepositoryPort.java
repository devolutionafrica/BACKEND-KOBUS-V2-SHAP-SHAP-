package com.nsia.cobus.domain.port;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nsia.cobus.domain.models.FillialeModel;

@Repository
public interface ParametreRepositoryPort {

    List<FillialeModel> getAllFilliales();

    FillialeModel getFillialeById(String codeFilliale);

}
