package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;

import java.util.List;

public interface BalitaService {
    List<BalitaModel> getAllBalita();
    boolean addBalita(BalitaModel balita, UserModel user);
    BalitaModel getBalitaById(Long id);
    void deleteBalita(BalitaModel balita);
    void changeStatusBalita(BalitaModel balita);
    void updateBalita(BalitaModel balita);
    List<String> getListBalitaAge();

    BalitaModel getBalitaAktif(UserModel user);
    boolean hasFilledPertumbuhan(BalitaModel balita);
}
