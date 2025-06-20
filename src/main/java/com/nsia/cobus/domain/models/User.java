package com.nsia.cobus.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String userId;
    private String userTypeId;
    private String login;
    private String password;
    private int isWindowsAccount;
    private String email;
    private String mobile;
    private int isFirstConnection;
    private String uniqueClientIds;
    private String photoUrl;
    private String creationDate;
    private String clientKey;
    private String branchCode;
    private String clientNumber;
    private String clientName;
    private String clientFirstName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String postalAddress;
    private String phoneNumber;
    private String phoneNumber1;
    private String profession;
    private String civility;
    private String nationality;
    private String maritalStatus;
    private String residentialAddress;
    private String clientType;
    private String bankCode;
    private String agencyCode;
    private String accountNumber;
    private String ribKey;
    private String startDate;
    private String endDate;
    private String annualIncomeId;
    private String roles;

}