package com.nsia.cobus.api.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.config.JwtUtils;
import com.nsia.cobus.domain.ReadProfilInfo;
// import com.nsia.cobus.domain.LoadAllUser;
// import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.models.UserLoginAndPassword;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
// import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cobus/auth")
public class AuthController {

    // private final LoadAllUser loadAllUser;

    private final AuthenticationManager authenticationManager;
    private final ReadProfilInfo readProfilInfo;
    private final JwtUtils jwtUtils;
    // private final UtilisateurRepository utilisateurRepository;

    // @GetMapping("")
    // public User getUser(@RequestParam String username) {
    // return loadAllUser.doUserById(username);
    // }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserLoginAndPassword utilisateur) throws Exception {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(utilisateur.getUsername());
                Map<String, Object> body = new HashMap<>();
                // Utilisateur u=
                Object user = readProfilInfo.readProfilInfo(utilisateur.username);
                body.put("token", token);
                // body.put("CODE_FILIALE", body);
                body.put("user", user);
                // body.put("userId",
                // utilisateurRepository.findUserByUsername(utilisateur.getUsername()).getId());
                // body.put("role",
                // utilisateurRepository.findUserByUsername(utilisateur.getUsername()).getRoles());
                return ResponseEntity.status(HttpStatus.OK).body(body);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Donnée incorrecte");
        } catch (Exception e) {
            // log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error internal Service");
        }
    }

    @GetMapping(path = "/check-token")
    public ResponseEntity<?> checkToken(@RequestParam("token") String token) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(jwtUtils.isTokenExpired(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error internal Service");
        }
    }

}
