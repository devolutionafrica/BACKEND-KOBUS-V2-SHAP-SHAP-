package com.nsia.cobus.domain.port;

import java.util.List;

import com.nsia.cobus.domain.models.ChangePasswordModel;
import com.nsia.cobus.domain.models.ChangeUserInfoModel;
import com.nsia.cobus.domain.models.User;

public interface UserRepositoryPort {

    User saveUser(User user);

    // UserModel login(UserLoginAndPassword user);

    List<User> getAllUsers();

    double getEngagement(String username);

    User findUserByUsername(String username);

    Object getProfilinfoByUsername(String username);

    void firstConnexion(String username, String password, String newPassword, String phone, String email, String login);

    String updatePassword(ChangePasswordModel changePasswordModel);

    String updateUserInfo(ChangeUserInfoModel changeUserInfoModel);

    Integer getProfilCompletion(String username);

    String requestToResetPassword(String username, String email);

    String resetPassword(String username, String newPassword, String code);

}
