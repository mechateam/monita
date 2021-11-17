package ta.simonitadepan.monitadepan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.BalitaModel;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.service.BalitaService;
import ta.simonitadepan.monitadepan.service.UserService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/balita")
public class
BalitaController {
    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public String balitaPage(
            Model model
    ){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getListBalita().size() != 0) {
            model.addAttribute("emptyBalita", 0);
            model.addAttribute("listBalita", balitaService.getListBalitaLogin(user));
            model.addAttribute("listAge", balitaService.getListBalitaAgeLogin(user));
        } else {
            model.addAttribute("emptyBalita", 1);
        }
        return "page-balita";
    }

    @PostMapping("")
    public String createBalita(
            @ModelAttribute BalitaModel balita,
            RedirectAttributes redirectAttributes
    ){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(balitaService.addBalita(balita, user)) {
            redirectAttributes.addFlashAttribute("msgCreateSc", "Data Balita Berhasil Ditambahkan!");
        } else {
            redirectAttributes.addFlashAttribute("msgCreateEr", "Data Balita Gagal Ditambahkan, Periksa Tanggal Lahir!");
        }
        return "redirect:/balita";
    }

    @GetMapping("/delete/{id_balita}")
    public String deleteBalita(
            @PathVariable Long id_balita,
            RedirectAttributes redirectAttributes
    ){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        BalitaModel balita = balitaService.getBalitaById(id_balita);
        if (balita.getId_pengguna().equals(user)){
            balitaService.deleteBalita(balita);
            redirectAttributes.addFlashAttribute("msgDelete", "Data Balita Berhasil Dihapus!");
        }
        else{
            redirectAttributes.addFlashAttribute("msgDelete", "Telah terjadi kesalahan!");
        }

        return "redirect:/balita";
    }

    @PostMapping("/edit/{id_balita}")
    private String editBalita(
            @PathVariable Long id_balita,
            @ModelAttribute BalitaModel balita,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        model.addAttribute("balita", balita);
        try{
            balitaService.updateBalita(balita);
            redirectAttributes.addFlashAttribute("msgUpdateSc", "Data Balita Berhasil Diubah!");
            return "redirect:/balita";
        } catch (NoSuchElementException e){ }
        redirectAttributes.addFlashAttribute("msgUpdateEr", "Data Balita Gagal Diubah!!");
        return "redirect:/balita";
    }

    @GetMapping("/status/{id_balita}")
    public String statusBalita(
            @PathVariable Long id_balita,
            RedirectAttributes redirectAttributes
    ){
        BalitaModel balita = balitaService.getBalitaById(id_balita);
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(balita.getId_pengguna().getUsername());
        System.out.println(user.getUsername());
        if (balita.getId_pengguna().getUsername() != user.getUsername()){
            return "redirect:/";
        }
        balitaService.changeStatusBalita(balita, user);
        return "redirect:/";
    }
}
