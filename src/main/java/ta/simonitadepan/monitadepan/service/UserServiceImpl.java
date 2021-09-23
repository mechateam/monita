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
    public void addUser(UserModel user) {
        System.out.println("password sblm di encrypt "+ user.getPassword());
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        System.out.println("password sblm di encrypt "+ user.getPassword());
        userDb.save(user);
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
}

