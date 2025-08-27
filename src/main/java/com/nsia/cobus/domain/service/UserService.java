package com.nsia.cobus.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.models.ChangeUserInfoModel;
import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.port.UserRepositoryPort;
import com.nsia.cobus.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    private final EmailService emailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private String passwordEncoder(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public List<User> getUsers() {
        return userRepositoryPort.getAllUsers();
    }

    public User findUserbyUsername(String username) {
        return userRepositoryPort.findUserByUsername(username);
    }

    // public User getUserById(Long id) {
    // return userRepositoryPort.getProfilinfoByUsername(id);
    // }

    public Object getUserinfoByUsername(String username) {
        return userRepositoryPort.getProfilinfoByUsername(username);
    }

    public void updateSetFirstConnexion(String username, String newPassword, String password, String email,
            String phone, String login) {
        userRepositoryPort.firstConnexion(username, password, newPassword, phone, email, login);
    }

    public String updatePassword(ChangePasswordModel changePasswordModel) {
        return userRepositoryPort.updatePassword(changePasswordModel);
    }

    public String updateChangeUserInfo(ChangeUserInfoModel user) {
        return userRepositoryPort.updateUserInfo(user);
    }

    public double getTauxEngagement(String username) {
        return userRepositoryPort.getEngagement(username);
    }

    public Integer getTauxCompletionClientInfo(String username) {
        return userRepositoryPort.getProfilCompletion(username);
    }

    public String requestToChangePassword(String username, String email) {
        try {
            userRepositoryPort.requestToResetPassword(username, email);
            return "Demande de changement de mot de passe envoyée";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'envoi de la demande de changement de mot de passe";
        }
    }

    public String resetPassword(String username, String newPassword, String code) {
        try {
            return userRepositoryPort.resetPassword(username, newPassword, code);

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la réinitialisation du mot de passe";
        }
    }

}
