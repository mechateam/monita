package ta.simonitadepan.monitadepan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.ImunisasiModel;
import ta.simonitadepan.monitadepan.service.BalitaService;
import ta.simonitadepan.monitadepan.service.ImunisasiService;
import ta.simonitadepan.monitadepan.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/imunisasi")
public class ImunisasiController {

    @Autowired
    ImunisasiService imunisasiService;

    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;


    @GetMapping("")
    public String imunisasiPage(
            Model model
    ){
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        List<ImunisasiModel> listImunisasiBelum = imunisasiService.getListBelumImunisasi(balita);

        model.addAttribute("listImunisasiBelum",listImunisasiBelum);
        model.addAttribute("balita",balita);

        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("tahun"));
        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("bulan"));

        if (listImunisasiBelum.size() ==0){
            model.addAttribute("emptyImunisasi",1);
        }

        return "home-imunisasi";
    }

    @GetMapping("/status/{idImunisasi}")
    public String sudahImunisasi(Model model, @PathVariable("idImunisasi") Long idImunisasi,
                                 RedirectAttributes redirectAttributes){
        ImunisasiModel imunisasi = imunisasiService.findImunisasiById(idImunisasi);

        imunisasiService.sudahImunisasi(imunisasi);

        redirectAttributes.addFlashAttribute("msgCreateSc", "Berhasil menambahkan imunisasi!");

        return "redirect:/imunisasi";
    }

    @GetMapping("/riwayat")
    public String riwayatImunisasi(
            Model model
    ){
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        List<ImunisasiModel> listImunisasi = balita.getListImunisasi();

        model.addAttribute("listImunisasi",listImunisasi);
        model.addAttribute("balita",balita);

        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("tahun"));
        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("bulan"));

        return "riwayat-imunisasi";
    }
}
