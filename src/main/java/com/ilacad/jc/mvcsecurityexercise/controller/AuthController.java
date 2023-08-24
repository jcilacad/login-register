package com.ilacad.jc.mvcsecurityexercise.controller;

import com.ilacad.jc.mvcsecurityexercise.dto.UserDto;
import com.ilacad.jc.mvcsecurityexercise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {

        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registration (@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {

        boolean isUserExists = userService.isUserExists(user);

        if (isUserExists) {
            result.rejectValue("email", null, "Email already exists!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/register";
        }

        userService.saveUser(user);

        return "redirect:/register?success";

    }

    @GetMapping("/users")
    public String users (Model model) {

        List<UserDto> userDtoList = userService.findAll();
        model.addAttribute("users", userDtoList);

        return "users";
    }

}
