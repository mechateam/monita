package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import org.springframework.stereotype.Repository;
import ta.simonitadepan.monitadepan.model.ImunisasiModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalitaDb extends JpaRepository<BalitaModel, Long> {
}
