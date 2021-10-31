package ta.simonitadepan.monitadepan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PeriodePerkembanganModel;
import ta.simonitadepan.monitadepan.model.PerkembanganBalitaModel;
import ta.simonitadepan.monitadepan.model.PertanyaanPerkembanganModel;
import ta.simonitadepan.monitadepan.repository.PerkembanganBalitaDb;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PerkembanganBalitaServiceImpl implements PerkembanganBalitaService {

    @Autowired
    PerkembanganBalitaDb perkembanganBalitaDb;

    @Autowired
    BalitaService balitaService;

    @Autowired
    PertanyaanPerkembanganService pertanyaanPerkembanganService;

    @Override
    public PerkembanganBalitaModel getPerkembanganById(Long id) {
        return perkembanganBalitaDb.getById(id);
    }

    @Override
    public List<String> processDiagnosis(Integer countYa) {
        List<String> diagnosisList = new ArrayList<String>();
        if (countYa == 9 || countYa == 10) {
            diagnosisList.add("Perkembangan Sesuai Umur");
            diagnosisList.add( "Selamat! perkembangan balita sudah sesuai umur. Lanjutkan stimulasi sesuai umur balita!");
            return diagnosisList;
        }
        if (countYa == 7 || countYa == 8) {
            diagnosisList.add("Perkembangan Meragukan");
            diagnosisList.add("Lakukan stimulasi lebih sering dengan penuh kasing sayang! Apabila hasil pemeriksaan selanjutnya juga meragukan, rujuk ke puskemas atau rumah sakit rujukan tumbuh kembang level 1.");
            return diagnosisList;
        }
        if (countYa > -1 && countYa <= 6 ) {
            diagnosisList.add("Perkembangan Menyimpang");
            diagnosisList.add("Rujuk ke rumah sakit rujukan tumbuh kembang level 1!");
            return diagnosisList;
        }
        return null;
    }

    @Override
    public List<String> processDiagnosisTipe(BalitaModel balita, Integer countGH, Integer countGK, Integer countB, Integer countS) {
        List<String> resultDiagnosisTipe = Arrays.asList("a", "b", "c", "d");
        int GH=0; int GK=0; int B=0; int S=0;
        for (PertanyaanPerkembanganModel p : pertanyaanPerkembanganService.getAllPertanyaanByBalita(balita)) {
            if (p.getTipe().equals("Gerak Halus")) {GH++;}
            if (p.getTipe().equals("Gerak Kasar")) {GK++;}
            if (p.getTipe().equals("Bicara dan Bahasa")) {B++;}
            if (p.getTipe().equals("Sosialisasi dan Kemandirian")) {S++;}
        }
        if (countGH == 0) {resultDiagnosisTipe.set(0, "Tidak ada penilaian gerak halus pada periode ini");}
        if (countGK == 0) {resultDiagnosisTipe.set(1, "Tidak ada penilaian gerak kasar pada periode ini");}
        if (countB == 0) {resultDiagnosisTipe.set(2, "Tidak ada penilaian bicara dan bahasa pada periode ini");}
        if (countS == 0) {resultDiagnosisTipe.set(3, "Tidak ada penilaian sosialisasi dan kemandirian pada periode ini");}
        if (countGH != 0 || countGK != 0 || countB !=0 || countS !=0) {
            resultDiagnosisTipe.set(0, countGH==GH ? "Baik" : "Perhatian");
            resultDiagnosisTipe.set(1, countGK==GK ? "Baik" : "Perhatian");
            resultDiagnosisTipe.set(2, countB==B ? "Baik" : "Perhatian");
            resultDiagnosisTipe.set(3, countS==S ? "Baik" : "Perhatian");
        }
        return resultDiagnosisTipe;
    }

    @Override
    public void savePerkembanganBalita(BalitaModel balita, List<String> diagnosis, PeriodePerkembanganModel periode, List<String> diagnosisTipe) {
        Date now = new Date();
        Integer umur = (balitaService.calculateAge(balita.getBirth_date()).get("tahun") * 12) + balitaService.calculateAge(balita.getBirth_date()).get("bulan");

        PerkembanganBalitaModel perkembangan = new PerkembanganBalitaModel();
        perkembangan.setInput_date(now);
        perkembangan.setInput_age(String.valueOf(umur));
        perkembangan.setIdBalita(balita);
        perkembangan.setIdPeriode(periode);
        perkembangan.setDiagnosis(diagnosis.get(0));
        perkembangan.setDeskripsi_diagnosis(diagnosis.get(1));
        perkembangan.setDiagnosis_gerak_halus(diagnosisTipe.get(0));
        perkembangan.setDiagnosis_gerak_kasar(diagnosisTipe.get(1));
        perkembangan.setDiagnosis_bicara_bahasa(diagnosisTipe.get(2));
        perkembangan.setDiagnosis_sosialisasi(diagnosisTipe.get(3));
        perkembanganBalitaDb.save(perkembangan);
    }

    @Override
    public List<PerkembanganBalitaModel> getPerkembanganByPeriodeAndBalita(PeriodePerkembanganModel periode, BalitaModel balitaModel){
        return perkembanganBalitaDb.findAllByIdPeriodeAndIdBalita(periode,balitaModel);
    }
}
