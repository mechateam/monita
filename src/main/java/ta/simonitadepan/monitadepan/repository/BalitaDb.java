package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import org.springframework.stereotype.Repository;

@Repository
public interface BalitaDb extends JpaRepository<BalitaModel, Long> {
}
