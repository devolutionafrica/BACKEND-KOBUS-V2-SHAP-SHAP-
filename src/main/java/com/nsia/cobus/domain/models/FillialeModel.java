package com.nsia.cobus.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillialeModel {
    private Long id;
    private String codeFiliale;
    private String libelle;
    private String currency;
    private String apiUrl;
    private String callCenterFilliale;
    private String mailFilliale;
    private String codeFillialePaiement;
    private String mobilePagePied;
    private String urlVideoPublicitaire;
}
