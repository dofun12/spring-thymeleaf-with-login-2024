package org.lemanoman.springthymeleaf;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StartupService implements ApplicationRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    public StartupService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByUsername("admin") != null) {
            return;
        }
        User user = new User();
        user.setUsername("admin");
        final String password = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);


        User guest = new User();
        guest.setUsername("guest");
        final String passwordGuest = "guest";
        guest.setPassword(passwordEncoder.encode(passwordGuest));
        guest.setEnabled(true);

        userRepository.save(guest);

        List<Role> roles = new ArrayList<>();
        String[] roleNames = {"ROLE_USER", "ROLE_ADMIN"};
        for (String roleName : roleNames) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            roles.add(role);
        }
        guest.setRoles(Collections.singletonList(roles.getFirst()));
        user.setRoles(roles);

        System.out.println("Creating user: " + user.getUsername()+" with password: "+password);

        userRepository.save(user);
    }
}