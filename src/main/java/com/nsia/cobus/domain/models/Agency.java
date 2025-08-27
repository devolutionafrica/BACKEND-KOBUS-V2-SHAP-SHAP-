package com.nsia.cobus.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agency {

    // String idAgence;
    String nameAgence;
    String localisationAgence;
    String phoneAgence;
    Long longitude;
    Long latitude;

}
