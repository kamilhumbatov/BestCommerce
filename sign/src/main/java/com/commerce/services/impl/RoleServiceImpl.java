package com.commerce.services.impl;

import com.commerce.common.enums.RoleName;
import com.commerce.common.exception.models.RolenameInvalidException;
import com.commerce.models.Role;
import com.commerce.repository.RoleRepository;
import com.commerce.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName).orElseThrow(RolenameInvalidException::new);
    }
}
