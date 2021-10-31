package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.FaskesModel;
import ta.simonitadepan.monitadepan.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface FaskesDb extends JpaRepository<FaskesModel, Long> {

    List<FaskesModel> findAllByKelurahan(String kelurahan);

}
