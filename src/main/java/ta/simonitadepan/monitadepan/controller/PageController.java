package ta.simonitadepan.monitadepan.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String landingPage(){
        return "page-home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
