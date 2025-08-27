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
public class Contract {
    private String contractNumber; // NumeroContrat
    private String productCode; // CodeProduit
    private String productName; // Produit
    private String policyStartDate; // DateDebutPolice (considérer LocalDate)
    private String policyEndDate; // DateFinPolice (considérer LocalDate)
    private String policyConventionSignatureDate; // DateSignatureConventionPolice (considérer LocalDate)
    private String policyDuration; // DureePolice
    private String policyType; // TypePolice
    private String contributionPeriodicity; // PeriodicieCotisation
    private BigDecimal capital; // Capital (Utiliser BigDecimal pour la précision financière)
    private String annuityDuration; // DUREE_RENTE (peut être null)
    private BigDecimal periodicPremium; // PrimePeriodique (Utiliser BigDecimal pour la précision financière)
    private String annuityPeriodicity; // PeriodiciteRente (peut être null)

    private String subscriberNumber; // NumeroSouscripteur
    private String subscriberName; // NomSouscripteur
    private String subscriberFirstName; // PrenomsSouscripteur (peut être null)
    private String subscriberDateOfBirth; // DateNaissanceSouscripteur (peut être null, considérer LocalDate)
    private String subscriberPlaceOfBirth; // LieuNaissanceSouscripteur
    private String subscriberPhoneNumber; // TelephoneSouscripteur
    private String subscriberPostalAddress; // AdressePostaleSouscripteur

    private String insuredNumber; // NumeroAssure
    private String insuredName; // NomAssure
    private String insuredFirstName; // PrenomsAssure
    private String insuredDateOfBirth; // DateNaissanceAssure (considérer LocalDate)
    private String insuredPlaceOfBirth; // LieuNaissanceAssure
    private String insuredPhoneNumber; // TelephoneAssure
    private String insuredPostalAddress; // AdressePostaleAssure
    private String insuredProfession; // ProfessionAssure (peut être null)
    private String insuredMaritalStatus; // SituationMatrimonialeAssure (peut être null)
    private String insuredEmail; // eMailAssure (peut être null)
    private String insuredGender; // SexeAssure ("M" | "F" | string)
    private String policyStatus; // EtatPolice
    private double annuityAmount; // MontantDeLaRente (Utiliser BigDecimal pour la précision financière)
    private String productDescription;
}
