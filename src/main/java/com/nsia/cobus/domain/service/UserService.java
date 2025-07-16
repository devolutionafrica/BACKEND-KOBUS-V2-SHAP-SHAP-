package com.nsia.cobus.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.models.ChangeUserInfoModel;
import com.nsia.cobus.domain.models.User;
import com.nsia.cobus.domain.port.UserRepositoryPort;

@Service
public record UserService(
        UserRepositoryPort userRepositoryPort) {

    public List<User> getUsers() {
        return userRepositoryPort.getAllUsers();
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

}
