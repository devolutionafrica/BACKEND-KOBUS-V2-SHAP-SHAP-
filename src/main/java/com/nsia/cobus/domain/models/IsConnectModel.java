package com.nsia.cobus.domain.models;

import java.util.Date;

import lombok.Data;

@Data
public class IsConnectModel {

    private String userName;
    private String token;
    private String compteType;
    private Date expiration;

}
