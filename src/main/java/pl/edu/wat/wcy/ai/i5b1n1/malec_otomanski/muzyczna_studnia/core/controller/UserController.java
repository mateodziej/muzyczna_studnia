package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getUser(Principal principal){
        return userService.getUser(principal.getName());
    }

    @PostMapping("/{username}")
    public void add(@PathVariable String username){
        userService.add(username);
    }

    @PutMapping("/{username}/city/{cityName}")
    public void updateCity(@PathVariable String username, @PathVariable String cityName){
        userService.add(username);
    }
}