package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.UserModel;

public interface UserDb extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);
}
