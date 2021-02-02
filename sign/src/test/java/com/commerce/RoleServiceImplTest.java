package com.commerce;

import com.commerce.common.enums.RoleName;
import com.commerce.common.exception.models.RolenameInvalidException;
import com.commerce.models.Role;
import com.commerce.repository.RoleRepository;
import com.commerce.services.RoleService;
import com.commerce.services.impl.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleServiceImpl.class)
@ContextConfiguration(classes = {RoleServiceImpl.class})
public class RoleServiceImplTest {

    private Role role;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName(RoleName.ROLE_MERCHANT);
    }

    @Test
    public void givenUsernameOrEmail() {
        when(roleRepository.findByName(RoleName.ROLE_MERCHANT)).thenReturn(Optional.of(role));

        assertThat(roleService.findByName(RoleName.ROLE_MERCHANT).getId()).isEqualTo(1);
        verify(roleRepository, times(1)).findByName(RoleName.ROLE_MERCHANT);
    }

    @Test
    public void findRoleNotFound(){
        when(roleRepository.findByName(RoleName.ROLE_MERCHANT)).thenThrow(new RolenameInvalidException());

        assertThatThrownBy(() -> roleService.findByName(RoleName.ROLE_MERCHANT))
                .isInstanceOf(RolenameInvalidException.class)
                .hasMessage("Invalid Role!");
        verify(roleRepository,times(1)).findByName(RoleName.ROLE_MERCHANT);
    }
}
