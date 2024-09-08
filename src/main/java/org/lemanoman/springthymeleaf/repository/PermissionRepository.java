package org.lemanoman.springthymeleaf.repository;

import org.lemanoman.springthymeleaf.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}