package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.model.PertanyaanPerkembanganModel;
import ta.simonitadepan.monitadepan.repository.PertanyaanPerkembanganDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PertanyaanPerkembanganServiceImpl implements PertanyaanPerkembanganService {
    @Autowired
    PertanyaanPerkembanganDb pertanyaanPerkembanganDb;

    @Autowired
    BalitaService balitaService;

    @Autowired
    PeriodePerkembanganService periodePerkembanganService;

    public List<PertanyaanPerkembanganModel> getAllPertanyaanByBalita(BalitaModel balita) {
        PeriodePerkembanganModel periode = periodePerkembanganService.getCurrentPeriodeBalita(balita);
        return pertanyaanPerkembanganDb.getPertanyaanPerkembanganModelByPeriode(periode.getId_periode());
    }
}
