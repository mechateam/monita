package ta.simonitadepan.monitadepan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.service.BalitaService;
import ta.simonitadepan.monitadepan.service.FaskesService;
import ta.simonitadepan.monitadepan.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserProfileController {
    @Autowired
    UserService userService;

    @Autowired
    BalitaService balitaService;

    @GetMapping("/profil")
    public String getProfilPage(Model model){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        BalitaModel balita = balitaService.getBalitaAktif(user);
        Integer tahun = balitaService.calculateAge(balita.getBirth_date()).get("tahun");
        Integer bulan = balitaService.calculateAge(balita.getBirth_date()).get("bulan");

        String umur = "Usia " + tahun + " tahun " + bulan + " bulan";

        if (balita != null){
            model.addAttribute("balita", balita);
            model.addAttribute("umur",umur);
        }
        else{
            model.addAttribute("empty","Harap tambah anak atau pilih anak");
        }
        return "page-profil";
    }

    @GetMapping("/profil/{username}")
    public String getDetailPage(@PathVariable String username, Model model){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "detail-profil";
    }

    @GetMapping("/profil/ubah/{username}")
    public String getFormUbahProfil (@PathVariable String username, Model model){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "ubah-profil";
    }

    @PostMapping("/profil/ubah/{username}")
    public String editProfil(@ModelAttribute UserModel user, Model model, RedirectAttributes redirectAttributes){
        userService.changeUser(user);
        return "redirect:/profil/"+user.getUsername();
    }

    @GetMapping("/profil/ubah-password/{username}")
    public String getFormUbahPassword (@ModelAttribute UserModel user, Model model){
        if (!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return "redirect:/profil/"+user.getUsername();
        }

        model.addAttribute("user", user);
        return "ubah-sandi";
    }

    @PostMapping("/profil/ubah-password/{username}")
    public String editPassword (
            @ModelAttribute UserModel user,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("rePassword") String rePassword,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ){
        UserModel userModel = userService.getUserByUsername(user.getUsername());

        if(!(user.getPassword().equals(rePassword))){
            System.out.println(user.getPassword());
            System.out.println(rePassword);
            System.out.println("masuk");
            model.addAttribute("message", "Konfirmasi Password Baru tidak sesuai ");
            return "redirect:/profil/ubah-password/"+user.getUsername();
        }

        // Cek Password dan retype Password && cek duplikat username
        if (user.getPassword().equals(rePassword)) {
            // Save Password
            userService.changePassword(userModel, oldPassword, user.getPassword());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            System.out.println("lama : "+ oldPassword);
            System.out.println("baru : "+ user.getPassword());
            System.out.println("konf : "+ rePassword);

            model.addAttribute("user",user);
            return "page-home";
        }

        model.addAttribute("message", "Password Lama tidak sesuai ");
        return "redirect:/profil/ubah-password/"+user.getUsername();

    }
}
