package ta.simonitadepan.monitadepan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.model.PertanyaanPerkembanganModel;

import java.util.List;

@Transactional
@Repository
public interface PertanyaanPerkembanganDb extends JpaRepository<PertanyaanPerkembanganModel, Long> {

    @Modifying
    @Query(value = "select * from pertanyaan_perkembangan where id_periode = (:id_periode)", nativeQuery = true)
    List<PertanyaanPerkembanganModel> getPertanyaanPerkembanganModelByPeriode(@Param("id_periode") Long idPeriode);
}
