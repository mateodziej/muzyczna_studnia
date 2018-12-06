package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserDto;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.UserService;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.impl.UserServiceImpl;

import java.security.Principal;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(Principal principal){
        userService.add(principal.getName());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Principal principal, @RequestBody UserDto userDto){
        userService.update(principal.getName(), userDto);
    }
}
