package ta.simonitadepan.monitadepan.service;

import ta.simonitadepan.monitadepan.model.FaskesModel;

import java.util.List;

public interface FaskesService {
    List<FaskesModel> getAllFaskes();

    List<FaskesModel> findByKelurahan(String kelurahan);

}
