package com.nsia.cobus.api.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.api.dto.FirstUserInfo;
import com.nsia.cobus.config.JwtUtils;
import com.nsia.cobus.domain.ReadProfilInfo;
import com.nsia.cobus.domain.ReadRateEngagement;
import com.nsia.cobus.domain.SetUpdateFirstConnexionInformation;
import com.nsia.cobus.domain.UpdatePassword;
import com.nsia.cobus.domain.UpdateUserInfo;
import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.models.ChangeUserInfoModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kobus/user")
public class UserController {

    private final ReadProfilInfo readProfilInfo;
    private final SetUpdateFirstConnexionInformation setUpdateFirstConnexionInformation;
    private final JwtUtils jwtUtils;
    private final UpdatePassword updatePassword;
    private final UpdateUserInfo updateUserInfo;
    private final ReadRateEngagement readRateEngagement;

    @GetMapping("/profil")
    public ResponseEntity<?> getProfilInfo(
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").split(" ")[1];

        String username = jwtUtils.extractUsername(token);

        return ResponseEntity.ok(readProfilInfo.readProfilInfo(username));
    }

    @PutMapping("/user/first-connexion")
    public ResponseEntity<?> setReadProfilInfo(@RequestBody FirstUserInfo readProfilInfo, HttpServletRequest http) {

        String token = http.getHeader("Authorization").split(" ")[1];
        String username = jwtUtils.extractUsername(token);
        String newPassword = readProfilInfo.getNewPassword();
        String password = readProfilInfo.getPassword();
        String email = readProfilInfo.getEmail();
        String phone = readProfilInfo.getPhone();
        setUpdateFirstConnexionInformation.setUpdateFirstConnexionInformation(username, newPassword, password, email,
                phone, email);
        return ResponseEntity.ok("Bienvenue vos données ont été mis à jour avec succès");
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid ChangePasswordModel changePasswordModel) {

        return ResponseEntity.ok(updatePassword.updatePassword(changePasswordModel));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserInfo(@RequestBody @Valid ChangeUserInfoModel user) {
        return ResponseEntity.ok(updateUserInfo.doUpdateUserinfo(user));
    }

    @GetMapping("/{username}/engagement")
    public ResponseEntity<Double> getTauxEngagement(@PathVariable String username) {
        System.out.println("Appel engagement");
        return ResponseEntity.ok(readRateEngagement.doRead(username));
    }

}
