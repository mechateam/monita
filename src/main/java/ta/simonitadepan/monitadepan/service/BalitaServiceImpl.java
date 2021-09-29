package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.repository.BalitaDb;

import javax.transaction.Transactional;
import java.time.*;
import java.util.*;

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
        Date date = new Date();
        LocalDate today = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate birth = balita.getBirth_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period daysBetween = Period.between(birth, today);

        if (daysBetween.isNegative()) {
            return false;
        } else {
            balita.setId_pengguna(user);
            balita.setStatus(1);
            for (BalitaModel balitaLain : this.getAllBalita()) {
                balitaLain.setStatus(0);
            }
            balitaDb.save(balita);
            return true;
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
    public void statusBalita(BalitaModel balita) {
        for (BalitaModel balitaLain : this.getAllBalita()) {
            balitaLain.setStatus(0);
        }
        balita.setStatus(1);
    }

    @Override
    public void updateBalita(BalitaModel balita) {
        BalitaModel balitaTarget = this.getBalita(balita.getId_balita());
        balitaTarget.setName(balita.getName());
        balitaDb.save(balitaTarget);
    }

    private String calculateAge(Date birth) {
        Date today = new Date();
        Calendar calendarBirth = Calendar.getInstance();
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(today);
        calendarBirth.setTime(birth);

        int yearNow = calendarNow.get(Calendar.YEAR);
        int yearBirth = calendarBirth.get(Calendar.YEAR);
        int monthNow = calendarNow.get(Calendar.MONTH);
        int monthBirth = calendarBirth.get(Calendar.MONTH);
        int age = yearNow - yearBirth;
        int month = monthNow- monthBirth;

        if (monthBirth > monthNow) {
            age--;
            month = monthNow + 12 - monthBirth;
        }
        if (monthNow == monthBirth) {
            if (age == 0) {
                month = 1;
            } else {
                int dayNow = calendarNow.get(Calendar.DAY_OF_MONTH);
                int dayBirth = calendarBirth.get(Calendar.DAY_OF_MONTH);
                if (dayBirth > dayNow) {
                    age--;
                    month = 11;
                }
            }
        }

        if (age == 0) {
            return month + " bulan";
        }
        return age + " tahun " + month + " bulan";
    }

    @Override
    public List<String> getListBalitaAge() {
        LocalDate today = LocalDate.now();
        List<String> listAge = new ArrayList<String>();
        for (BalitaModel balita : this.getAllBalita()) {
            listAge.add(calculateAge(balita.getBirth_date()));
        }
        return listAge;
    }
}
