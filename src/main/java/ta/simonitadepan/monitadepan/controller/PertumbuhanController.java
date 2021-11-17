package ta.simonitadepan.monitadepan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.PertumbuhanBalitaModel;
import ta.simonitadepan.monitadepan.service.BalitaService;
import ta.simonitadepan.monitadepan.service.PertumbuhanService;
import ta.simonitadepan.monitadepan.service.ServerProperties;
import ta.simonitadepan.monitadepan.service.UserService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/pertumbuhan")
public class PertumbuhanController {

    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;

    @Autowired
    ServerProperties serverProperties;

    @Autowired
    PertumbuhanService pertumbuhanService;

    @GetMapping("")
    public String mainPagePertumbuhan(Model model, RedirectAttributes redirectAttributes){
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if (balita == null){
            redirectAttributes.addFlashAttribute("msgCreateEr", "Anda belum menambahkan balita, harap tambah balita!");
            return "redirect:/balita";
        }

        if (balitaService.hasFilledPertumbuhan(balita) == true){
            model.addAttribute("msg", "Selamat, anda sudah mengisi Data Pertumbuhan bulan ini");
        }
        else{
            model.addAttribute("msg","Masukkan data pertumbuhan Balita");
        }

        model.addAttribute("balita",balita);
        model.addAttribute("statusBalita",balitaService.hasFilledPertumbuhan(balita));
        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("tahun"));
        model.addAttribute("bulan",balitaService.calculateAge(balita.getBirth_date()).get("bulan"));


        return "home-pertumbuhan";

    }

    @PostMapping(value = "/add")
    public String addDataPertumbuhan(
            Model model, @ModelAttribute PertumbuhanBalitaModel pertumbuhan, RedirectAttributes redirectAttributes
    ){

        if (pertumbuhanService.addPertumbuhan(pertumbuhan)){
            redirectAttributes.addFlashAttribute("msgCreateSc", "Data Balita Berhasil Ditambahkan!");
        } else {
            redirectAttributes.addFlashAttribute("msgCreateEr", "Data Balita Gagal Ditambahkan, Periksa Tanggal Lahir!");
        }

        return "redirect:/pertumbuhan";
    }

    @GetMapping(value = "/detail/{idPertumbuhan}")
    public String detailPertumbuhan(
            Model model, @PathVariable Long idPertumbuhan, RedirectAttributes redirectAttributes
    ){
        PertumbuhanBalitaModel pertumbuhan = pertumbuhanService.getPertumbuhanById(idPertumbuhan);
        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if (pertumbuhan.getId_balita().getId_balita() != balita.getId_balita() || pertumbuhan == null){
            redirectAttributes.addFlashAttribute("msgCreateEr", "Data Pertumbuhan yang anda inginkan tidak ada");
            return "redirect:/pertumbuhan";
        }

        List<String> listDiagnosis = Arrays.asList(pertumbuhan.getDiagnosis().split(","));
        List<String> listDeskripsiDiagnosis = Arrays.asList(pertumbuhan.getDeskripsi_diagnosis().split(","));

        Float IMT = pertumbuhan.getBerat_badan()/(pertumbuhan.getTinggi_badan()*pertumbuhan.getTinggi_badan());

        model.addAttribute("tahun",balitaService.calculateAge(balita.getBirth_date()).get("tahun"));
        model.addAttribute("bulan",balitaService.calculateAge(balita.getBirth_date()).get("bulan"));
        model.addAttribute("balita",balita);
        model.addAttribute("imt",IMT);
        model.addAttribute("pertumbuhan",pertumbuhan);
        model.addAttribute("listDiagnosis",listDiagnosis);
        model.addAttribute("listDeskripsiDiagnosis",listDeskripsiDiagnosis);
        return "detail-pertumbuhan";






    }

}
