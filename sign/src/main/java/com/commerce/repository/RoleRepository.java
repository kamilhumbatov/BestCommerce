package com.commerce.repository;

import com.commerce.common.enums.RoleName;
import com.commerce.common.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
