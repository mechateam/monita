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
    public void addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        userDb.save(user);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
