package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.UserModel;

import java.util.Optional;

public interface UserDb extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);

    UserModel findByEmail(String email);
    UserModel findByResetPasswordToken(String token);
    Optional<UserModel> findById(Long id);
}
