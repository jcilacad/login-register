package com.ilacad.jc.mvcsecurityexercise.controller;

import com.ilacad.jc.mvcsecurityexercise.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

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

}
