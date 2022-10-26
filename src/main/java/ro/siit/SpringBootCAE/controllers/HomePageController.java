package ro.siit.SpringBootCAE.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomePageController {

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("info", "hello!");
        return "home";
    }


    // Login form
    @GetMapping( "/login")
    public String logInPage(Model model){
        model.addAttribute("info", "hello!");
        return "loginForm";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "loginForm";
    }



}
