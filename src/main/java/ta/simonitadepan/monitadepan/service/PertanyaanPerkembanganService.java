package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.model.PertanyaanPerkembanganModel;

import java.util.List;

public interface PertanyaanPerkembanganService {
    List<PertanyaanPerkembanganModel> getAllPertanyaanByBalita(BalitaModel balita);
}
