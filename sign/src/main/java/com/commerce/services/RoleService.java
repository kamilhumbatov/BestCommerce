package com.commerce.services;

import com.commerce.common.enums.RoleName;
import com.commerce.common.models.Role;

public interface RoleService {
    Role findByName(RoleName roleName);
}
