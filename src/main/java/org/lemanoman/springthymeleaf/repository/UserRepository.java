package org.lemanoman.springthymeleaf.repository;

import org.lemanoman.springthymeleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}