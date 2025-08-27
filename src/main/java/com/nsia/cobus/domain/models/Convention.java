package com.nsia.cobus.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Convention {
    private Long IDE_CONVENTION;
    private String IDE_CLIENT_UNIQUE;
    private String NUMERO_DE_CONVENTION;
    private String LIBELLE_CONVENTION;
}