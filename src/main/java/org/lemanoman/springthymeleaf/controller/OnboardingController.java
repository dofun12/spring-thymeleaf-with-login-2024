package org.lemanoman.springthymeleaf.controller;

import org.lemanoman.springthymeleaf.dto.SimpleUserDto;
import org.lemanoman.springthymeleaf.model.User;
import org.lemanoman.springthymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/onboarding")
public class OnboardingController {

    private final UserService userService;

    @Autowired
    public OnboardingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/create"})
    public String showOnboardingForm(Model model) {
        model.addAttribute("user", new SimpleUserDto());
        model.addAttribute("isNew", true);
        return "onboarding";
    }

    @PostMapping("/save")
    public String saveUser(SimpleUserDto user, BindingResult result, Model model) {
        String message = "User saved successfully!";
        model.addAttribute("isNew", true);
        try {
            userService.saveSimpleUser(user);
            model.addAttribute("isNew", false);
        } catch (Exception e) {

            model.addAttribute("error", e.getMessage());
            message = "Error saving user!";
            model.addAttribute("hasErrors", true);
        }
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "onboarding";
    }
}