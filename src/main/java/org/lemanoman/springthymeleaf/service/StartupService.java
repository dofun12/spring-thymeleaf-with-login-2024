package org.lemanoman.springthymeleaf.service;

import org.lemanoman.springthymeleaf.model.Role;
import org.lemanoman.springthymeleaf.repository.RoleRepository;
import org.lemanoman.springthymeleaf.model.User;
import org.lemanoman.springthymeleaf.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StartupService implements ApplicationRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    public StartupService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleService.createDefaultRoles();
    }
}