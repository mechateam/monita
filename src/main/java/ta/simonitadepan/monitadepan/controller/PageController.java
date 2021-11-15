package ta.simonitadepan.monitadepan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ta.simonitadepan.monitadepan.model.*;
import ta.simonitadepan.monitadepan.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {

    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;

    @Autowired
    PertumbuhanService pertumbuhanService;

    @Autowired
    ImunisasiService imunisasiService;

    @Autowired
    PeriodePerkembanganService periodePerkembanganService;

    @Autowired
    PerkembanganBalitaService perkembanganBalitaService;

    @GetMapping("/")
    public String landingPage(Model model){


        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if (balita==null){
            return "redirect:/balita";
        }

        PertumbuhanBalitaModel pertumbuhanBalitaModel = pertumbuhanService.getPertumbuhanBulanIni(balita.getListPertumbuhan());
        Map<String, Integer> umur = balitaService.calculateAge(balita.getBirth_date());

        if (pertumbuhanBalitaModel == null){
            model.addAttribute("isi",false);
        }
        else{
            model.addAttribute("isi",true);
            if (pertumbuhanService.getHasilDiagnosisBulanIni(pertumbuhanBalitaModel.getDiagnosis())){
                model.addAttribute("diagnosis", "SESUAI");
            }
            else{
                model.addAttribute("diagnosis", "PERHATIAN");
            }
            model.addAttribute("berat", pertumbuhanBalitaModel.getBerat_badan());
            model.addAttribute("tinggi", pertumbuhanBalitaModel.getTinggi_badan());
        }

        List<ImunisasiModel> listImunisasiBelum = imunisasiService.getListBelumImunisasi(balita);

        if (listImunisasiBelum == null || listImunisasiBelum.size() == 0 ) {
            model.addAttribute("imunisasi_sudah", "Anda sudah melakukan imunisasi periode ini.");
        }else{
            model.addAttribute("imunisasi",listImunisasiBelum.get(0));
        }

        PeriodePerkembanganModel periode = periodePerkembanganService.getCurrentPeriodeBalita(balita);
        List<PerkembanganBalitaModel> listPerkembanganBalita = perkembanganBalitaService.getPerkembanganByPeriodeAndBalita(periode, balita);

        if (listPerkembanganBalita == null || listPerkembanganBalita.size() == 0){
            model.addAttribute("perkembangan_sudah", "Anda belum mengisi data perkembangan periode ini");
        }
        else{
            model.addAttribute("perkembangan",listPerkembanganBalita.get(0));
        }

        model.addAttribute("balita",balita);
        model.addAttribute("bulan",umur.get("bulan"));
        model.addAttribute("tahun",umur.get("tahun"));
        return "page-home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
