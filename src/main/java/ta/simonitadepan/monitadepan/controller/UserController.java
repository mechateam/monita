package ta.simonitadepan.monitadepan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ta.simonitadepan.monitadepan.model.UserModel;
import ta.simonitadepan.monitadepan.service.FaskesService;
import ta.simonitadepan.monitadepan.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    FaskesService faskesService;

    @Autowired
    UserService userService;


    @RequestMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("listFaskes",faskesService.getAllFaskes());

        return "page-register";
    }

    @PostMapping("/register")
    public String registerNewUser(
            @ModelAttribute UserModel user,
            Model model,
            RedirectAttributes redirect
    ){
        System.out.println("Masuk");
        userService.addUser(user);

        model.addAttribute("msg","Akun anda sudah dibuat, silahkan login");
        return "redirect:/login";

    }
}
