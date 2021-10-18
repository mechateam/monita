package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;

public interface PertumbuhanDb extends JpaRepository<PertumbuhanBalitaModel, Long> {
}
