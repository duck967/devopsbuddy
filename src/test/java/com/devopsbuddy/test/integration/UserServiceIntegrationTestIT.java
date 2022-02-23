package com.devopsbuddy.test.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UsersUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class UserServiceIntegrationTestIT {
    @Autowired
    private UserService userService;

    @Test
    public void testCreateNewUser() throws Exception {

        Set<UserRole> userRoles = new HashSet<>();
        User basicUser = UsersUtils.createBasicUser();
        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

        User user = userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
        assertNotNull(user);
        assertNotNull(user.getId());

    }
}
