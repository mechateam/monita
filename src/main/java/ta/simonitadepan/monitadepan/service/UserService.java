package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.UserModel;

public interface UserService {
    void addUser(UserModel user);
    String encrypt(String password);

    public UserModel getUserByUsername(String username);

    public String updateResetPasswordToken(String token, String email);
    public UserModel getByResetPasswordToken(String token);
    public void updatePassword(UserModel customer, String newPassword);

}
