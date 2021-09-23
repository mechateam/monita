package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.FaskesModel;
import ta.simonitadepan.monitadepan.model.UserModel;

public interface FaskesDb extends JpaRepository<FaskesModel, Long> {
}
