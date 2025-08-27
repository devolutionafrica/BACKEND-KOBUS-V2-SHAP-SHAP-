package com.nsia.cobus.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sinistre {
    private String policyNumber;
    private String claimNumber;
    private String beneficiaryNumber; // NumeroBeneficiaire
    private String beneficiaryName; // Beneficiaire
    private String beneficiaryDateOfBirth; // DateNaissanceBeneficiaire (consider LocalDate)
    private String beneficiaryPostalAddress; // AdressePostaleBeneficiaire
    private String beneficiaryPhoneNumber; // TelephoneBeneficiaire
    private String claimCause; // CauseSinistre
    private String claimStatus; // EtatSinistre
    private String claimType; // TypeSinistre
    private String claimLabel; // LibelleSinistre
    private String occurrenceDate; // DateSurvenance (consider LocalDate)
    private String declarationDate; // DateDeclaration (consider LocalDate)
    private String openingDate; // DateOuverture (consider LocalDate)
    private String closingDate; // DateCloture (String | null, consider LocalDate)
    private String situationDate; // DateSituation (consider LocalDate)
    private BigDecimal estimatedAmount; // MontantEstime (number | null, use BigDecimal for currency)
    private BigDecimal settledAmount; // MontantRegle (use BigDecimal for currency)
    private String branchCode; // CodeFiliale (String | null)
    private String settlementDate; // DateReglement (consider LocalDate)
    private Long settlementNumber; // NumeroReglement (number in TS -> Long in Java for IDs/numbers)
    private String settlementMode; // ModeReglement
}