package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;
import ta.simonitadepan.monitadepan.repository.PertumbuhanDb;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;

@Service
@Transactional
public class PertumbuhanServiceImpl implements PertumbuhanService {

    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;

    @Autowired
    ServerProperties serverProperties;

    @Autowired
    PertumbuhanDb pertumbuhanDb;

    @Override
    public PertumbuhanBalitaModel getPertumbuhanById(Long id){
        return pertumbuhanDb.getById(id);
    }

    @Override
    public boolean addPertumbuhan(PertumbuhanBalitaModel pertumbuhan) {

        BalitaModel balitaAktif = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        Integer umur = (balitaService.calculateAge(balitaAktif.getBirth_date()).get("tahun") * 12) + balitaService.calculateAge(balitaAktif.getBirth_date()).get("bulan");



        Float tinggi_badan = pertumbuhan.getTinggi_badan();
        Float berat_badan = pertumbuhan.getBerat_badan();
        Float IMT = berat_badan/(tinggi_badan*tinggi_badan);

        pertumbuhan.setDiagnosis(calculateBeratUmur(umur,berat_badan)[0] + ", "+calculateTinggiUsia(umur,tinggi_badan)[0]+", "+calculateIMT(umur,IMT)[0]);
        pertumbuhan.setDeskripsi_diagnosis(calculateBeratUmur(umur,berat_badan)[1] + ", "+calculateTinggiUsia(umur,tinggi_badan)[1]+", "+calculateIMT(umur,IMT)[1]);
        pertumbuhan.setId_balita(balitaAktif);
        pertumbuhan.setInput_age(String.valueOf(umur));
        pertumbuhan.setInput_date(new Date());

        if (pertumbuhanDb.save(pertumbuhan) != null){
            return true;
        }
        return false;
    }

    private String[] calculateBeratUmur(Integer umur, Float berat){
        String diagnosis = "";
        String deskripsi = "";
        Map<Integer, Float> tabelBeratUsiaLaki = serverProperties.getBeratusialaki().get(umur);

        if (berat < tabelBeratUsiaLaki.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Berat badan sangat kurang";
        }
        else if (berat >= tabelBeratUsiaLaki.get(-3) && berat < tabelBeratUsiaLaki.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Berat badan kurang";
        }
        else if(berat >= tabelBeratUsiaLaki.get(-2) && berat < tabelBeratUsiaLaki.get(1)){
            diagnosis+="Normal";
            deskripsi+="Berat badan normal";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Berat badan lebih";
        }

        return new String[] {diagnosis,deskripsi};

    }

    private String[] calculateTinggiUsia(Integer umur, Float tinggi){
        String diagnosis = "";
        String deskripsi = "";
        Map<Integer, Float> tabelTinggiUsiaLaki = serverProperties.getTbusialaki().get(umur);

        if (tinggi < tabelTinggiUsiaLaki.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Sangat Pendek";
        }
        else if (tinggi >= tabelTinggiUsiaLaki.get(-3) && tinggi < tabelTinggiUsiaLaki.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Pendek";
        }
        else if(tinggi >= tabelTinggiUsiaLaki.get(-2) && tinggi < tabelTinggiUsiaLaki.get(3)){
            diagnosis+="Normal";
            deskripsi+="Normal";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Tinggi";
        }

        return new String[] {diagnosis,deskripsi};

    }

    private String[] calculateIMT(Integer umur, Float IMT){
        String diagnosis = "";
        String deskripsi = "";

        Map<Integer, Float> tabelIMTLaki = serverProperties.getImtlaki().get(umur);

        if (IMT < tabelIMTLaki.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Buruk";
        }
        else if (IMT >= tabelIMTLaki.get(-3) && IMT < tabelIMTLaki.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Kurang";
        }
        else if(IMT >= tabelIMTLaki.get(-2) && IMT < tabelIMTLaki.get(1)){
            diagnosis+="Normal";
            deskripsi+="Normal";
        }
        else if(IMT >= tabelIMTLaki.get(1) && IMT < tabelIMTLaki.get(2)){
            diagnosis+="Perhatian";
            deskripsi+="Beresiko Gizi Lebih";
        }
        else if(IMT >= tabelIMTLaki.get(2) && IMT < tabelIMTLaki.get(3)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Lebih";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Gizi Buruk";
        }

        return new String[] {diagnosis,deskripsi};
    }



}
