package com.nsia.cobus.domain.models;

import lombok.Data;

@Data
public class IntegrateurPayModel {
    public String id;
    public String libelle;
    public int isActif;
    public String urlApi;
    public String urlApiRetour;
    public String logo;
    public String desription;
}