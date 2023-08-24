package com.ilacad.jc.mvcsecurityexercise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;imort org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    @GetMapping("/index")
    public String home() {
        return "index";
    }

}
