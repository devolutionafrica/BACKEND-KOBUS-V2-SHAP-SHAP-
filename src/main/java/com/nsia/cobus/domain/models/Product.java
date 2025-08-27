package com.nsia.cobus.domain.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Product {

    private String name;
    private String ideProduit;
    private String codeProduction;
    private String descriptionProduit;
    private String codeProduit;
    private String codeFilliale;
    private String descNatureProduit;
    private String famille;
}
