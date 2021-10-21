package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;

import java.util.Date;

public interface PertumbuhanDb extends JpaRepository<PertumbuhanBalitaModel, Long> {
}
