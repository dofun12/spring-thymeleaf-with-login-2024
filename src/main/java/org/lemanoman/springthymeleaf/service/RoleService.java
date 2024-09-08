package org.lemanoman.springthymeleaf.service;

import org.lemanoman.springthymeleaf.model.Role;
import org.lemanoman.springthymeleaf.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService extends GenericServiceImpl<Role, Long> {
    final static String ROLE_USER = "ROLE_USER";
    final static String ROLE_ADMIN = "ROLE_ADMIN";
    private final RoleRepository repository;
    public RoleService(RoleRepository repository) {
        super(repository);
        this.repository = repository;
    }
    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }



    public void createDefaultRoles(){
        if(findByName(ROLE_USER).isEmpty()){
            Role role = new Role();
            role.setName(ROLE_USER);
            repository.save(role);
        }
        if(findByName(ROLE_ADMIN).isEmpty()){
            Role role = new Role();
            role.setName(ROLE_ADMIN);
            repository.save(role);
        }
    }




}
