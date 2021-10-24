package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.repository.BalitaDb;
import ta.simonitadepan.monitadepan.repository.PeriodePerkembanganDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PeriodePerkembanganServiceImpl implements PeriodePerkembanganService {
    @Autowired
    PeriodePerkembanganDb periodePerkembanganDb;

    @Autowired
    BalitaService balitaService;

    @Override
    public List<PeriodePerkembanganModel> getAllPeriode() {
        return periodePerkembanganDb.findAll();
    }

    @Override
    public PeriodePerkembanganModel getCurrentPeriodeBalita(BalitaModel balita) {
        int ageMonth = balitaService.calculateAge(balita.getBirth_date()).get("tahun")*12 + balitaService.calculateAge(balita.getBirth_date()).get("bulan");
        if (ageMonth <= 3) {
            return periodePerkembanganDb.getById((long) 1);
        }
        if (ageMonth > 3 && ageMonth <= 6) {
            return periodePerkembanganDb.getById((long) 2);
        }
        if (ageMonth > 6 && ageMonth <= 9) {
            return periodePerkembanganDb.getById((long) 3);
        }
        if (ageMonth > 9 && ageMonth <= 12) {
            return periodePerkembanganDb.getById((long) 4);
        }
        if (ageMonth > 12 && ageMonth <= 15) {
            return periodePerkembanganDb.getById((long) 5);
        }
        if (ageMonth > 15 && ageMonth <= 18) {
            return periodePerkembanganDb.getById((long) 6);
        }
        if (ageMonth > 18 && ageMonth <= 21) {
            return periodePerkembanganDb.getById((long) 7);
        }
        if (ageMonth > 21 && ageMonth <= 24) {
            return periodePerkembanganDb.getById((long) 8);
        }
        if (ageMonth > 24 && ageMonth <= 30) {
            return periodePerkembanganDb.getById((long) 9);
        }
        if (ageMonth > 30 && ageMonth <= 36) {
            return periodePerkembanganDb.getById((long) 10);
        }
        if (ageMonth > 36 && ageMonth <= 42) {
            return periodePerkembanganDb.getById((long) 11);
        }
        if (ageMonth > 42 && ageMonth <= 48) {
            return periodePerkembanganDb.getById((long) 12);
        }
        if (ageMonth > 48 && ageMonth <= 54) {
            return periodePerkembanganDb.getById((long) 13);
        }
        if (ageMonth > 24 && ageMonth <= 60) {
            return periodePerkembanganDb.getById((long) 14);
        }
        return null;
    }
}
