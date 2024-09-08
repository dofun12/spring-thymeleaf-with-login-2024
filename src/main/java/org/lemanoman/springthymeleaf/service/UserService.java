package org.lemanoman.springthymeleaf.service;

import org.lemanoman.springthymeleaf.InvalidUserException;
import org.lemanoman.springthymeleaf.dto.SimpleUserDto;
import org.lemanoman.springthymeleaf.model.Role;
import org.lemanoman.springthymeleaf.model.User;
import org.lemanoman.springthymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class UserService extends GenericServiceImpl<User, Long> {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, RoleService roleService) {
        super(repository);
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }


    public User saveSimpleUser(SimpleUserDto user) throws InvalidUserException {
        User newUser = new User();
        newUser.setEnabled(true);

        if(user.getUsername().isBlank()) {
            throw new InvalidUserException("Username cannot be blank");
        }
        if(findByUsername(user.getUsername()) != null) {
            throw new InvalidUserException("Username already exists");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new InvalidUserException("Passwords do not match");
        }
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        Collection<Role> roles = new ArrayList<>();
        if(this.findAll().isEmpty()) {
            roleService.findByName(RoleService.ROLE_ADMIN).ifPresent(roles::add);
            newUser.setRoles(roles);
            return save(newUser);
        }
        roleService.findByName(RoleService.ROLE_USER).ifPresent(roles::add);
        newUser.setRoles(roles);
        return save(newUser);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}