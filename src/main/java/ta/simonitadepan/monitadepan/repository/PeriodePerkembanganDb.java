package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;

public interface PeriodePerkembanganDb extends JpaRepository<PeriodePerkembanganModel, Long> {
    PeriodePerkembanganModel findByIdPeriode(Long id_periode);
}
