package com.nsia.cobus.api.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.config.JwtUtils;
import com.nsia.cobus.domain.ReadProfilInfo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cobus/profil")
public class UserController {

    private final ReadProfilInfo readProfilInfo;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> getProfilInfo(
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").split(" ")[1];

        String username = jwtUtils.extractUsername(token);

        return ResponseEntity.ok(readProfilInfo.readProfilInfo(username));
    }

}
