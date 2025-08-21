package com.nsia.cobus.api.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.config.JwtUtils;
import com.nsia.cobus.domain.ReadProfilInfo;
import com.nsia.cobus.domain.ReadUserByusername;
import com.nsia.cobus.domain.RequestToResetPassword;
import com.nsia.cobus.domain.ResetPassWord;
import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.models.IsConnectModel;
// import com.nsia.cobus.domain.LoadAllUser;
// import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.models.UserLoginAndPassword;
import com.nsia.cobus.domain.service.UserService;
import com.nsia.cobus.service.EmailService;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kobus/auth")
public class AuthController {

    // private final LoadAllUser loadAllUser;

    private final AuthenticationManager authenticationManager;
    private final ReadUserByusername readUserByusername;
    private final JwtUtils jwtUtils;
    private final RequestToResetPassword requestToResetPassword;
    private final ResetPassWord resetPassWord;

    @PostMapping("/login")
    ResponseEntity<IsConnectModel> login(@RequestBody UserLoginAndPassword utilisateur) throws Exception {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
            IsConnectModel isConnectModel = new IsConnectModel();
            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(utilisateur.getUsername());
                User user = readUserByusername.doRead(utilisateur.username);
                isConnectModel.setToken(token);
                isConnectModel.setUserName(utilisateur.username);
                isConnectModel.setCompteType(user.getUserTypeId());

                isConnectModel.setExpiration(jwtUtils.extractExpiDate(token));
                return ResponseEntity.status(HttpStatus.OK).body(isConnectModel);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            // log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
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

    @PostMapping("/request-reset/{username}")
    public ResponseEntity<String> requestResetPassword(@PathVariable String username, @RequestParam String email) {

        return ResponseEntity.ok().body(requestToResetPassword.doRead(username, email));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword,
            @RequestParam String code) {

        return ResponseEntity.ok().body(resetPassWord.resetPassword(username, newPassword, code));
    }

}
