package ta.simonitadepan.monitadepan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.service.BalitaService;
import ta.simonitadepan.monitadepan.service.UserService;

@Controller
public class PertumbuhanController {

    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/pertumbuhan",method = RequestMethod.GET)
    public String mainPagePertumbuhan(Model model){
        System.out.println("HALO 1");

        BalitaModel balita = balitaService.getBalitaAktif(userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if (balita == null){
            System.out.println("HALO 2");
            return "login";
        }

        if (balitaService.hasFilledPertumbuhan(balita) == true){
            System.out.println("HALO 3");
            model.addAttribute("msg", "Selamat, anda sudah mengisi Data Pertumbuhan bulan ini");
        }
        System.out.println("HALO 4");

        return "home-pertumbuhan";

    }
}
