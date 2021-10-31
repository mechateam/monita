package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.model.PerkembanganBalitaModel;

import java.util.List;

public interface PerkembanganBalitaService {
    PerkembanganBalitaModel getPerkembanganById(Long id);
    List<String> processDiagnosis(Integer countYa);
    List<String> processDiagnosisTipe(BalitaModel balita, Integer countGH, Integer countGK, Integer countB, Integer countS);
    void savePerkembanganBalita(BalitaModel balita, List<String> diagnosis, PeriodePerkembanganModel periode, List<String> diagnosisTipe);

    List<PerkembanganBalitaModel> getPerkembanganByPeriodeAndBalita(PeriodePerkembanganModel periode, BalitaModel balitaModel);
}
