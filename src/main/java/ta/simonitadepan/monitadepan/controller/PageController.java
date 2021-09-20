package ta.simonitadepan.monitadepan.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String landingPage(){
        return "page-home";
    }
}
