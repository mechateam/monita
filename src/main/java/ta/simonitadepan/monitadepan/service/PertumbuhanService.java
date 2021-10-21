package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;

import java.util.Date;
import java.util.List;

public interface PertumbuhanService {
    boolean addPertumbuhan(PertumbuhanBalitaModel pertumbuhan);
    PertumbuhanBalitaModel getPertumbuhanById(Long id);
    PertumbuhanBalitaModel getPertumbuhanBulanIni(List<PertumbuhanBalitaModel> listPertumbuhan);

    boolean getHasilDiagnosisBulanIni(String diagnosis);
}
