package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.ImunisasiModel;
import ta.simonitadepan.monitadepan.model.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BalitaService {
    List<BalitaModel> getAllBalita();
    List<BalitaModel> getListBalitaLogin(UserModel user);
    List<String> getListBalitaAgeLogin(UserModel user);
    boolean addBalita(BalitaModel balita, UserModel user);
    BalitaModel getBalitaById(Long id);
    void deleteBalita(BalitaModel balita);
    void changeStatusBalita(BalitaModel balita, UserModel user);
    void updateBalita(BalitaModel balita);
    List<String> getListBalitaAge();
    BalitaModel getBalitaAktif(UserModel user);
    Map<String, Integer> calculateAge(Date birth);
    boolean hasFilledPerkembangan (BalitaModel balita);
    boolean hasFilledPertumbuhan(BalitaModel balita);

    List<ImunisasiModel> listImunisasiBalitaSorted(BalitaModel balita);
}
