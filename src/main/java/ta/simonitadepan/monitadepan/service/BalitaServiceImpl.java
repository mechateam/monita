package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PerkembanganBalitaModel;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;
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
    public BalitaModel getBalitaById(Long id) {
        return balitaDb.getById(id);
    }

    @Override
    public void deleteBalita(BalitaModel balita) {
        balitaDb.delete(balita);
    }

    @Override
    public void changeStatusBalita(BalitaModel balita) {
        for (BalitaModel balitaLain : this.getAllBalita()) {
            balitaLain.setStatus(0);
        }
        balita.setStatus(1);
    }

    @Override
    public void updateBalita(BalitaModel balita) {
        BalitaModel balitaTarget = this.getBalitaById(balita.getId_balita());
        balitaTarget.setName(balita.getName());
        balitaDb.save(balitaTarget);
    }

    @Override
    public Map<String, Integer> calculateAge(Date birth) {
        Map<String,Integer> tahunBulan = new HashMap<String, Integer>();

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
                month = 0;
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
            tahunBulan.put("tahun",0);
        }
        else{
            tahunBulan.put("tahun",age);
        }


        tahunBulan.put("bulan",month);

        return tahunBulan;
    }


    @Override
    public List<String> getListBalitaAge() {
        List<String> listAge = new ArrayList<String>();
        for (BalitaModel balita : this.getAllBalita()) {
            String txt = ""+ calculateAge(balita.getBirth_date()).get("tahun") + " tahun " + calculateAge(balita.getBirth_date()).get("bulan") + " bulan";
            listAge.add(txt);
        }
        return listAge;
    }

    @Override
    public List<String> getListBalitaAgeLogin(UserModel user) {
        List<String> listAge = new ArrayList<String>();
        for (BalitaModel balita : this.getListBalitaLogin(user)) {
            String txt = ""+ calculateAge(balita.getBirth_date()).get("tahun") + " tahun " + calculateAge(balita.getBirth_date()).get("bulan") + " bulan";
            listAge.add(txt);
        }
        return listAge;
    }

    @Override
    public BalitaModel getBalitaAktif(UserModel user){

        for (BalitaModel b: user.getListBalita()){
            if(b.getStatus() == 1){return b;}
        }

        return null;
    }

    @Override
    public boolean hasFilledPerkembangan (BalitaModel balita){
        for (PerkembanganBalitaModel kembang: balita.getListPerkembangan()){
            int tahun_input = kembang.getInput_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
            int bulan_input = kembang.getInput_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getValue();

            int tahun_now = LocalDateTime.now().getYear();
            int bulan_now = LocalDateTime.now().getMonthValue();

            if (tahun_input == tahun_now && bulan_input == bulan_now){
                return true;
            }

        }

        return false;
    }
    
    @Override
    public boolean hasFilledPertumbuhan(BalitaModel balita){
        for (PertumbuhanBalitaModel tumbuh: balita.getListPertumbuhan()){
            int tahun_input = tumbuh.getInput_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
            int bulan_input = tumbuh.getInput_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getValue();

            int tahun_now = LocalDateTime.now().getYear();
            int bulan_now = LocalDateTime.now().getMonthValue();

            if (tahun_input == tahun_now && bulan_input == bulan_now){
                return true;
            }

        }

        return false;
    }

    @Override
    public List<BalitaModel> getListBalitaLogin(UserModel user){
        return user.getListBalita();
    }
}
