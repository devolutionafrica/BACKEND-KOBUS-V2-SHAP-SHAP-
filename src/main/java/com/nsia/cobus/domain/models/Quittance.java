package com.nsia.cobus.domain.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Quittance {

    private String nameRent;
    private String quittanceNumber;
    private String policeNumber;
    private String quittanceState;
    private double amount;

}
