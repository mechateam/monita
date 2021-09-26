package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;

import java.util.List;

public interface BalitaService {
    List<BalitaModel> getAllBalita();
    void addBalita(BalitaModel balita, UserModel user);
    BalitaModel getBalita(Long id);
    void deleteBalita(BalitaModel balita);
    void updateBalita(BalitaModel balita);
    List<String> getListBalitaAge();
}
