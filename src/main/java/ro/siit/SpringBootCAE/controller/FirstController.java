package ro.siit.SpringBootCAE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping ( "/")
    public String homePage(Model model){
        model.addAttribute("info", "this is the start");
        return "index";
    }

}
