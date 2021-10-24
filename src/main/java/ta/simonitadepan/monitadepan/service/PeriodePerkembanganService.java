package ta.simonitadepan.monitadepan.service;


import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;

import java.util.List;

public interface PeriodePerkembanganService {
    List<PeriodePerkembanganModel> getAllPeriode();
    PeriodePerkembanganModel getCurrentPeriodeBalita(BalitaModel balita);
}
