package com.nsia.cobus.api.controllers.Contrat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.config.JwtUtils;
import com.nsia.cobus.domain.ReadAvisSituationForContrat;
import com.nsia.cobus.domain.ReadCotisationForContrat;
import com.nsia.cobus.domain.ReadDetailsInformationForContrat;
import com.nsia.cobus.domain.ReadSinistreForContrat;
import com.nsia.cobus.domain.service.ContratService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/cobus/contrat")
@RequiredArgsConstructor
public class ContratController {

    private final JwtUtils jwtUtils;
    private final ContratService contratService;
    private final ReadSinistreForContrat readSinistreForContrat;
    private final ReadDetailsInformationForContrat readDetailsInformationForContrat;
    private final ReadAvisSituationForContrat readAvisSituationForContrat;
    private final ReadCotisationForContrat readCotisationForContrat;

    @GetMapping("")
    ResponseEntity<?> fetchContratByUser(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (!authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorization.split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        if (username != null) {
            contratService.getAllContracts(username);
        }

        System.out.println(String.format("Le nom d'utilisateur %s\n\n", username));

        return ResponseEntity.ok(contratService.getAllContracts(username));

    }

    @GetMapping("/{contrat_police}")
    public ResponseEntity<?> getDetailsContrat(@PathVariable(name = "contrat_police") String contratid,
            HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        String username = jwtUtils.extractUsername(token);

        return ResponseEntity
                .ok(readDetailsInformationForContrat.readDetailsInformationForContrat(contratid, username));

    }

    @GetMapping("/{police}/prestations")
    public ResponseEntity<?> getSinistre(@PathVariable(name = "police") String contratid,
            HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        String username = jwtUtils.extractUsername(token);

        return ResponseEntity
                .ok(readSinistreForContrat.readSinistreForContrat(contratid));

    }

    @GetMapping("/{police}/cotisation")
    public ResponseEntity<?> getCotisationByContrat(
            @PathVariable(name = "police") String contratid, @RequestParam String dateDeb,
            @RequestParam String dateFin) {

        return ResponseEntity.ok().body(readCotisationForContrat.readCotisationForContrat(contratid, dateDeb, dateFin));
    }

    @GetMapping("/{police}/avis_situation")
    public ResponseEntity<?> getAviSituation(
            @PathVariable(name = "police") String police, @RequestParam String codeFiliale,
            @RequestParam String year) {

        return ResponseEntity.ok()
                .body(readAvisSituationForContrat.readAvisSituation(codeFiliale, police, year));
    }

}
