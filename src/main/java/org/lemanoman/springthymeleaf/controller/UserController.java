package org.lemanoman.springthymeleaf.controller;

import org.lemanoman.springthymeleaf.RoleRepository;
import org.lemanoman.springthymeleaf.User;
import org.lemanoman.springthymeleaf.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/user-add/{id}")
    private String userAddByUser(@PathVariable String id,Authentication authentication, Model model) {
        var role = authentication.getAuthorities().stream().filter(a -> a.getAuthority().equals("ROLE_ADMIN")).findFirst();
        if (!role.isPresent()) {
            return "home";
        }

        model.addAttribute("roles", roleRepository.findAll());


        if (!model.containsAttribute("user")) {
            model.addAttribute("user", userRepository.findById(Long.valueOf(id)).orElse(new User()));
        }
        return "user/user-add";


    }

    @GetMapping("/user-add")
    private String userAdd(Authentication authentication, Model model) {
        var role = authentication.getAuthorities().stream().filter(a -> a.getAuthority().equals("ROLE_ADMIN")).findFirst();
        if (!role.isPresent()) {
            return "home";
        }

        model.addAttribute("roles", roleRepository.findAll());


        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "user/user-add";


    }

    @PostMapping("/save")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "redirect:/user/user-add/"+user.getId();

        }catch (Exception ex){
            model.addAttribute("error",ex.getMessage());
            model.addAttribute("user",user);
            return "error";
        }
    }


}