package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {

    @GetMapping("/api/auth")
    public Principal getPrincipal(Principal principal){
        OAuth2Authentication auth;
        auth = (OAuth2Authentication) principal;
        System.out.println("Principal " + auth.getUserAuthentication().getDetails().getClass().getName());
        return principal;
    }
}
