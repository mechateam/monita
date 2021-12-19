package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;
import ta.simonitadepan.monitadepan.repository.PertumbuhanDb;

import javax.transaction.Transactional;
import java.util.*;

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
        Float tinggi_badan_meter= tinggi_badan/100;
        Float berat_badan = pertumbuhan.getBerat_badan();
        Float IMT = berat_badan/(tinggi_badan_meter*tinggi_badan_meter);


        pertumbuhan.setDiagnosis(calculateBeratUmur(umur,berat_badan,balitaAktif.getGender())[0] + ", "+calculateTinggiUsia(umur,tinggi_badan,balitaAktif.getGender())[0]+", "+calculateIMT(umur,IMT,balitaAktif.getGender())[0] +", " + calculateBBperTB(berat_badan,tinggi_badan,balitaAktif.getGender(),umur)[0]);
        pertumbuhan.setDeskripsi_diagnosis(calculateBeratUmur(umur,berat_badan,balitaAktif.getGender())[1] + ", "+calculateTinggiUsia(umur,tinggi_badan,balitaAktif.getGender())[1]+", "+calculateIMT(umur,IMT,balitaAktif.getGender())[1] +", " + calculateBBperTB(berat_badan,tinggi_badan,balitaAktif.getGender(),umur)[1]);
        pertumbuhan.setId_balita(balitaAktif);
        pertumbuhan.setInput_age(String.valueOf(umur));
        pertumbuhan.setInput_date(new Date());

        if (pertumbuhanDb.save(pertumbuhan) != null){
            return true;
        }
        return false;
    }

    private String[] calculateBeratUmur(Integer umur, Float berat, int gender){
        String diagnosis = "";
        String deskripsi = "";
        Map<Integer, Float> tabelBeratUmur;
        if (gender == 0){
            tabelBeratUmur = serverProperties.getBeratusiaperempuan().get(umur);
        }
        else{
            tabelBeratUmur = serverProperties.getBeratusialaki().get(umur);
        }


        if (berat < tabelBeratUmur.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Berat badan sangat kurang";
        }
        else if (berat >= tabelBeratUmur.get(-3) && berat < tabelBeratUmur.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Berat badan kurang";
        }
        else if(berat >= tabelBeratUmur.get(-2) && berat < tabelBeratUmur.get(1)){
            diagnosis+="Normal";
            deskripsi+="Berat badan normal";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Berat badan lebih";
        }

        return new String[] {diagnosis,deskripsi};

    }

    private String[] calculateTinggiUsia(Integer umur, Float tinggi, int gender){
        String diagnosis = "";
        String deskripsi = "";
        Map<Integer, Float> tabelTinggiUsia;
        if (gender == 0){
            tabelTinggiUsia = serverProperties.getTbusiaperempuan().get(umur);
        }
        else{
            tabelTinggiUsia = serverProperties.getTbusialaki().get(umur);
        }

        if (tinggi < tabelTinggiUsia.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Sangat Pendek";
        }
        else if (tinggi >= tabelTinggiUsia.get(-3) && tinggi < tabelTinggiUsia.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Pendek";
        }
        else if(tinggi >= tabelTinggiUsia.get(-2) && tinggi < tabelTinggiUsia.get(3)){
            diagnosis+="Normal";
            deskripsi+="Normal";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Tinggi";
        }

        return new String[] {diagnosis,deskripsi};

    }

    private String[] calculateIMT(Integer umur, Float IMT,int gender){
        String diagnosis = "";
        String deskripsi = "";

        Map<Integer, Float> tabelIMT;


        if (gender == 0){
            tabelIMT = serverProperties.getImtperempuan().get(umur);
        }
        else{
            tabelIMT = serverProperties.getImtlaki().get(umur);
        }

        if (IMT < tabelIMT.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Buruk";
        }
        else if (IMT >= tabelIMT.get(-3) && IMT < tabelIMT.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Kurang";
        }
        else if(IMT >= tabelIMT.get(-2) && IMT < tabelIMT.get(1)){
            diagnosis+="Normal";
            deskripsi+="Normal";
        }
        else if(IMT >= tabelIMT.get(1) && IMT < tabelIMT.get(2)){
            diagnosis+="Perhatian";
            deskripsi+="Beresiko Gizi Lebih";
        }
        else if(IMT >= tabelIMT.get(2) && IMT < tabelIMT.get(3)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Lebih";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Gizi Buruk";
        }

        return new String[] {diagnosis,deskripsi};
    }

    private String[] calculateBBperTB(Float berat, Float tinggi,int gender, Integer umur){
        String diagnosis = "";
        String deskripsi = "";
        Map<Integer, Float> tabelBBperTB;

        tinggi = (float) roundToHalf(tinggi);

        if (gender==0 && umur <24){
            tabelBBperTB = serverProperties.getBbpertbperempuan0().get(tinggi);
            if (tinggi < 45){
                tabelBBperTB = serverProperties.getBbpertbperempuan0().get((float)45);
            }
            else if (tinggi >110){
                tabelBBperTB = serverProperties.getBbpertbperempuan0().get((float)110);
            }
        }
        else if(gender==0 && umur >=24){
            tabelBBperTB = serverProperties.getBbpertbperempuan24().get(tinggi);
            if (tinggi<65){
                tabelBBperTB = serverProperties.getBbpertbperempuan24().get((float)65);
            }
            else if(tinggi > 120){
                tabelBBperTB = serverProperties.getBbpertbperempuan24().get((float)120);
            }
        }
        else if(gender==1 && umur <24){
            tabelBBperTB = serverProperties.getBbpertblaki0().get(tinggi);
            if (tinggi < 45){
                tabelBBperTB = serverProperties.getBbpertblaki0().get((float)45);
            }
            else if (tinggi >110){
                tabelBBperTB = serverProperties.getBbpertblaki0().get((float)110);
            }
        }
        else{
            tabelBBperTB = serverProperties.getBbpertblaki24().get(tinggi);
            if (tinggi<65){
                tabelBBperTB = serverProperties.getBbpertblaki24().get((float)65);
            }
            else if(tinggi > 120){
                tabelBBperTB = serverProperties.getBbpertblaki24().get((float)120);
            }
        }


        if (berat < tabelBBperTB.get(-3)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Buruk";
        }
        else if (berat >= tabelBBperTB.get(-3) && berat < tabelBBperTB.get(-2)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Kurang";
        }
        else if(berat >= tabelBBperTB.get(-2) && berat < tabelBBperTB.get(1)){
            diagnosis+="Normal";
            deskripsi+="Normal";
        }
        else if(berat >= tabelBBperTB.get(1) && berat < tabelBBperTB.get(2)){
            diagnosis+="Perhatian";
            deskripsi+="Beresiko Gizi Lebih";
        }
        else if(berat >= tabelBBperTB.get(2) && berat < tabelBBperTB.get(3)){
            diagnosis+="Perhatian";
            deskripsi+="Gizi Lebih";
        }
        else{
            diagnosis+="Perhatian";
            deskripsi+="Gizi Buruk";
        }

        return new String[] {diagnosis,deskripsi};

    }

    @Override
    public PertumbuhanBalitaModel getPertumbuhanBulanIni(List<PertumbuhanBalitaModel> listPertumbuhan){
        Date tanggal_hari_ini = new Date();
        Integer bulan = tanggal_hari_ini.getMonth();
        Integer tahun = tanggal_hari_ini.getYear();

        for (PertumbuhanBalitaModel tumbuh: listPertumbuhan){
            if (tumbuh.getInput_date().getMonth() == bulan && tumbuh.getInput_date().getYear() == tahun ){
                return tumbuh;
            }
        }
        return null;
    }

    @Override
    public boolean getHasilDiagnosisBulanIni(String diagnosis){
        List<String> myList = new ArrayList<String>(Arrays.asList(diagnosis.split(", ")));
        for (String diag: myList){
            if (diag.equals("Perhatian")){
                return false;
            }
        }
        return true;
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }


}
