package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.repository.UserDb;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDb userDb;

    @Override
    public UserModel getUserByUsername(String username){
        return userDb.findByUsername(username);
    }

    @Override
    public UserModel addUser(UserModel user) {
        if (userDb.findByUsername(user.getUsername()) != null){
            return null;
        }
        if (userDb.findByEmail(user.getEmail()) != null){
            return null;
        }
        if (userDb.findByPhone(user.getPhone()) != null){
            return null;
        }
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        userDb.save(user);
        return user;
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public String updateResetPasswordToken(String token, String email) {
        UserModel user = userDb.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userDb.save(user);
            return "token_success";
        } else {
            return null;
        }
    }

    @Override
    public UserModel getByResetPasswordToken(String token) {
        if (token == null){
            return null;
        }
        return userDb.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(UserModel user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userDb.save(user);
    }

    @Override
    public String getGender(int gender){
        if(gender == 0){
            return "Perempuan";
        }
        return "Laki-Laki";
    }

    @Override
    public void changeUser(UserModel user) {
        userDb.save(user);
    }

    @Override
    public boolean changePassword(UserModel user, String oldPassword, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Ini user" + user.getUsername());
        System.out.println("Ini password" + user.getPassword());

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encrypt(newPassword));
            userDb.save(user);

            return true;
        }

        return false;
    }
}

