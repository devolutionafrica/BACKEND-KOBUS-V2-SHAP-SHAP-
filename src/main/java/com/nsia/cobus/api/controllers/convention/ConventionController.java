package com.nsia.cobus.api.controllers.convention;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.config.JwtUtils;
import com.nsia.cobus.domain.ReadAllContratByConvention;
import com.nsia.cobus.domain.ReadAvisSituationForConvention;
import com.nsia.cobus.domain.ReadConventionForCorporate;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kobus/corporate/convention")
public class ConventionController {

    private final ReadConventionForCorporate readConventionForCorporate;
    private final ReadAllContratByConvention readAllContratByConvention;
    private final ReadAvisSituationForConvention readAvisSituationForConvention;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> findAllConvention(
            HttpServletRequest request) {

        if (request.getHeader("Authorization") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le token est manquant");
        }

        String token = request.getHeader("Authorization").split(" ")[1];

        String username = jwtUtils.extractUsername(token);

        return ResponseEntity.ok(readConventionForCorporate.doReadConvention(username));
    }

    @GetMapping("/{idconvention}/contrat")
    public ResponseEntity<?> getAllContrat(@PathVariable int idconvention, HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le token est manquant");
        }
        String token = request.getHeader("Authorization").split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        return ResponseEntity.ok(readAllContratByConvention.readAllContratByConvention(username, idconvention));
    }

    @GetMapping("/{idconvention}/avis_situation")
    public ResponseEntity<?> getAvisSituation(@PathVariable String idconvention, HttpServletRequest request,
            @RequestParam("ANNEE") String year) {
        if (request.getHeader("Authorization") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le token est manquant");
        }
        if (year == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le paramètre année est manquant");
        }
        String token = request.getHeader("Authorization").split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        return ResponseEntity
                .ok(readAvisSituationForConvention.readAvisSituationForConvention(username, idconvention, year));
    }

}
