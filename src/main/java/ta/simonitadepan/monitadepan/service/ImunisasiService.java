package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.ImunisasiModel;

import java.util.List;

public interface ImunisasiService {

    List<ImunisasiModel> getListBelumImunisasi(BalitaModel balita);
    ImunisasiModel findImunisasiById(Long id);

    ImunisasiModel sudahImunisasi(ImunisasiModel imunisasi);
}
