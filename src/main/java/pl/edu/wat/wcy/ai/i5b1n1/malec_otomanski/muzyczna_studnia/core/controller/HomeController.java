package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.UserService;

import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome(Principal principal) {
        if(principal != null) return "redirect:/aplikacja";

        return "index";
    }

    @GetMapping("/aplikacja")
    public String getApp(Principal principal, Model model){
        if(principal == null) return "redirect:/";
        String name = principal.getName();
        addUserIfNotExists(name);
        OAuth2Authentication auth;
        auth = (OAuth2Authentication) principal;
        Map<String, Object> details = (Map<String, Object>) auth.getUserAuthentication().getDetails();
        model.addAttribute("username", (String)details.get("name").toString());

        return "layout";
    }

    @GetMapping("/cms")
    public String getCms(Principal principal) {
        if(principal == null) return "redirect:/";

        return "app";
    }

    private void addUserIfNotExists(String name) {
        userService.add(name);
    }
}
