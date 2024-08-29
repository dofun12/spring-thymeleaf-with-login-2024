package org.lemanoman.springthymeleaf;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StartupService implements ApplicationRunner {
    private UserRepository userRepository;

    public StartupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByUsername("admin") != null) {
            return;
        }
        User user = new User();
        user.setUsername("admin");
        user.setPassword(UUID.randomUUID()+"");
        user.setEnabled(true);
        System.out.println("Creating user: " + user.getUsername()+" with password: "+user.getPassword());

        userRepository.save(user);
    }
}