package org.lemanoman.springthymeleaf;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        List<Role> roles = new ArrayList<>();
        String[] roleNames = {"ROLE_USER", "ROLE_ADMIN"};
        for (String roleName : roleNames) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            roles.add(role);
        }
        user.setRoles(roles);

        System.out.println("Creating user: " + user.getUsername()+" with password: "+password);

        userRepository.save(user);
    }
}