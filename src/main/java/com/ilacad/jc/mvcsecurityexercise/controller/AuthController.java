package com.ilacad.jc.mvcsecurityexercise.controller;

import com.ilacad.jc.mvcsecurityexercise.dto.UserDto;
import com.ilacad.jc.mvcsecurityexercise.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/login")
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userService.isUserAuthenticated(authentication)) {
            return "login";
        }

        return "redirect:/users";

    }

    @GetMapping("/register")
    public String register(Model model) {

        UserDto user = new UserDto();
        model.addAttribute("user", user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userService.isUserAuthenticated(authentication)) {
            return "register";
        }

        return "redirect:/users";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {

        boolean isUserExists = userService.isUserExists(user);

        if (isUserExists) {
            result.rejectValue("email", null, "Email already exists!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);

        return "redirect:/register?success";

    }

    @GetMapping("/users")
    public String users(Model model) {

        List<UserDto> userDtoList = userService.findAll();
        model.addAttribute("users", userDtoList);

        return "users";
    }

}
