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
public class BalitaController {
    @Autowired
    BalitaService balitaService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public String balitaPage(
            Model model
    ){
        model.addAttribute("listBalita", balitaService.getAllBalita());
        return "page-balita";
    }

    @PostMapping("")
    public String createBalita(
            @ModelAttribute BalitaModel balita,
            RedirectAttributes redirectAttributes
    ){
        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        balitaService.addBalita(balita, user);
        redirectAttributes.addFlashAttribute("msg", "Balita berhasil ditambahkan!");
        return "redirect:/balita";
    }

    @GetMapping("/delete/{id_balita}")
    public String deleteBalita(
            @PathVariable Long id_balita,
            RedirectAttributes redirectAttributes
    ){

        BalitaModel balita = balitaService.getBalita(id_balita);
        balitaService.deleteBalita(balita);
        redirectAttributes.addFlashAttribute("msg", "Balita berhasil dihapus!");
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
            redirectAttributes.addFlashAttribute("msg", "Subcost Centre successfully edited!");
            return "redirect:/balita";
        } catch (NoSuchElementException e){ }
        System.out.println("gagal update");
        redirectAttributes.addFlashAttribute("msg", "Subcost Centre successfully edited!");
        return "redirect:/balita";
    }
}
