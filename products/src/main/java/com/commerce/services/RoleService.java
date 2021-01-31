package com.commerce.services;

import com.commerce.enums.RoleName;
import com.commerce.models.Role;

public interface RoleService {
    Role findByName(RoleName roleName);
}
