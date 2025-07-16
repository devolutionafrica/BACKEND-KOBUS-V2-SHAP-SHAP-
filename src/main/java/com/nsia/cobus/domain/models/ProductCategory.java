package com.nsia.cobus.domain.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductCategory {
    private String name;
    private String nature;
}
