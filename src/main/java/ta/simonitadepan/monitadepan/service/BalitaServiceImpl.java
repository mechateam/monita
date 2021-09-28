package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.repository.BalitaDb;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BalitaServiceImpl implements BalitaService {
    @Autowired
    BalitaDb balitaDb;

    @Override
    public List<BalitaModel> getAllBalita() {
        return balitaDb.findAll();
    }

    @Override
    public boolean addBalita(BalitaModel balita, UserModel user) {
        Date now = new Date();
        if(balita.getBirth_date().compareTo(now) < 0){
            balita.setId_pengguna(user);
            balitaDb.save(balita);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BalitaModel getBalita(Long id) {
        return balitaDb.getById(id);
    }

    @Override
    public void deleteBalita(BalitaModel balita) {
        balitaDb.delete(balita);
    }

    @Override
    public void updateBalita(BalitaModel balita) {
        BalitaModel balitaTarget = this.getBalita(balita.getId_balita());
        balitaTarget.setName(balita.getName());
        balitaDb.save(balitaTarget);
    }

    @Override
    public List<String> getListBalitaAge() {
        LocalDate today = LocalDate.now();
        List<String> listAge = new ArrayList<String>();
        for (BalitaModel balita : this.getAllBalita()) {
            LocalDate birthday = LocalDate.of(balita.getBirth_date().getYear(), balita.getBirth_date().getMonth(), balita.getBirth_date().getDate());
            Period period = Period.between(birthday, today);
            if (period.getYears() == 1900){
                listAge.add(period.getMonths() + " bulan");
            } else {
                String year = period.toString();
                listAge.add(year.substring(4,5)+ " tahun " + period.getMonths() + " bulan ");
            }
        }
        return listAge;
    }
}
