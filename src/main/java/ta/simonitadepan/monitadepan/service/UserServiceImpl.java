package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void addUser(UserModel user) {userDb.save(user);}
}
