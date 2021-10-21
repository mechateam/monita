package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;

public interface PertumbuhanService {
    boolean addPertumbuhan(PertumbuhanBalitaModel pertumbuhan);
    PertumbuhanBalitaModel getPertumbuhanById(Long id);
}
