package ta.simonitadepan.monitadepan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.*;
import ta.simonitadepan.monitadepan.service.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PerkembanganController {

    @Autowired
    PertanyaanPerkembanganService pertanyaanPerkembanganService;

    @Autowired
    PerkembanganBalitaService perkembanganBalitaService;

    @Autowired
    PeriodePerkembanganService periodePerkembanganService;

    @Autowired
    UserService userService;

    @Autowired
    BalitaService balitaService;

    @GetMapping("/perkembangan")
    public String perkembanganPage(
            Model model
    ) {
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (balitaService.hasFilledPerkembangan(balita) == true){
            model.addAttribute("msg", "Selamat, anda sudah mengisi Data Perkembangan untuk rentang usia bulan ini");
        }
        else{
            model.addAttribute("msg","Masukkan Data Perkembangan Balita");
        }

        model.addAttribute("balita", balita);
        model.addAttribute("hasRiwayat", balita.getListPerkembangan().isEmpty() ? false : true);
        model.addAttribute("statusBalita",balitaService.hasFilledPerkembangan(balita));
        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("tahun"));
        model.addAttribute("bulan",balitaService.calculateAge(balita.getBirth_date()).get("bulan"));
        return "home-perkembangan";
    }

    @GetMapping("/perkembangan/tambah")
    public String perkembanganAddPage(
            Model model, RedirectAttributes attributes
    ) {
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (balitaService.hasFilledPerkembangan(balita) == true){
            attributes.addFlashAttribute("msg", "Selamat, anda sudah mengisi Data Perkembangan bulan ini");
            return "redirect:/perkembangan";
        }
        Map<String,String> map = new HashMap<String, String>();
        List<String> list = new ArrayList<String>();
        for (PertanyaanPerkembanganModel p : pertanyaanPerkembanganService.getAllPertanyaanByBalita(balita)) {
            map.put(p.getPertanyaan(), p.getTipe());
            list.add(p.getPertanyaan());
        }
        model.addAttribute("namaBalita", balita.getName());
        model.addAttribute("umur", balitaService.calculateAge(balita.getBirth_date()).get("tahun")*12 + balitaService.calculateAge(balita.getBirth_date()).get("bulan"));
        model.addAttribute("list", list);
        model.addAttribute("map", map);
        return "page-add-perkembangan";
    }

    @RequestMapping(value = "/perkembangan/tambah", method = RequestMethod.POST, params = {"toSave"})
    public String postPerkembangan(@RequestParam String result, @RequestParam String resultGH,
                                   @RequestParam String resultGK, @RequestParam String resultB, @RequestParam String resultS) {
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        List<String> diagnosisTipe = perkembanganBalitaService.processDiagnosisTipe(balita, Integer.valueOf(resultGH), Integer.valueOf(resultGK), Integer.valueOf(resultB), Integer.valueOf(resultS));
        List<String> diagnosisList = perkembanganBalitaService.processDiagnosis(Integer.valueOf(result));
        PeriodePerkembanganModel periode = periodePerkembanganService.getCurrentPeriodeBalita(balita);
        perkembanganBalitaService.savePerkembanganBalita(balita, diagnosisList, periode, diagnosisTipe);
        return "redirect:/perkembangan";
    }

    @GetMapping("/perkembangan/detail/{id_perkembangan}")
    public String perkembanganDetailPage(
            @PathVariable long id_perkembangan, Model model
    ) {
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        for (PerkembanganBalitaModel kembang : balita.getListPerkembangan()) {
            if(kembang.getId_perkembangan() == id_perkembangan){
                model.addAttribute("perkembangan", kembang);
                model.addAttribute("balita", balita);
                model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("tahun"));
                model.addAttribute("bulan",balitaService.calculateAge(balita.getBirth_date()).get("bulan"));
                return "detail-perkembangan";
            }
        }
        return "redirect:/perkembangan";
    }
}

