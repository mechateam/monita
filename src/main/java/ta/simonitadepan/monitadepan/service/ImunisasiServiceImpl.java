package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.ImunisasiModel;
import ta.simonitadepan.monitadepan.repository.ImunisasiDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ImunisasiServiceImpl implements ImunisasiService {

    @Autowired
    BalitaService balitaService;

    @Autowired
    ImunisasiDb imunisasiDb;

    @Override
    public List<ImunisasiModel> getListBelumImunisasi(BalitaModel balita){
        Map<String,Integer> umur = balitaService.calculateAge(balita.getBirth_date());
        Integer bulan = umur.get("bulan");

        List<ImunisasiModel> belumImunisasi = new ArrayList<>();

        for (ImunisasiModel imun: balita.getListImunisasi()){
            if (imun.getPeriode() <= bulan && imun.getStatus() == 0){
                belumImunisasi.add(imun);
            }
        }

        return belumImunisasi;
    }

    @Override
    public ImunisasiModel findImunisasiById(Long id){
        return imunisasiDb.findById(id).get();
    }

    @Override
    public ImunisasiModel sudahImunisasi(ImunisasiModel imunisasi){
        imunisasi.setStatus(1);

        imunisasiDb.save(imunisasi);

        return imunisasi;
    }
}
