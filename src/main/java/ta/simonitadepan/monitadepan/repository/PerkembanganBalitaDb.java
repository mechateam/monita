package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.model.PerkembanganBalitaModel;

import java.util.List;

@Transactional
@Repository
public interface PerkembanganBalitaDb extends JpaRepository<PerkembanganBalitaModel, Long> {

    List<PerkembanganBalitaModel> findAllByIdPeriodeAndIdBalita(PeriodePerkembanganModel periode, BalitaModel balitaModel);
//
//    @Modifying
//    @Query(value = "select * from perkembangan_balita where id_balita = (:id_balita)", nativeQuery = true)
//    List<PerkembanganBalitaModel> getAllPerkembanganByBalita(@Param("id_balita") Long idBalita);
}
