package ta.simonitadepan.monitadepan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;
import ta.simonitadepan.monitadepan.service.BalitaService;
import ta.simonitadepan.monitadepan.service.PertumbuhanService;
import ta.simonitadepan.monitadepan.service.UserService;

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
