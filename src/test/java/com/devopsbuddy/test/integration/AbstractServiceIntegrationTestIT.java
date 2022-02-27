package com.devopsbuddy.test.integration;

import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.service.UserService;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;

import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tedonema on 11/04/2016.
 */
public abstract class AbstractServiceIntegrationTestIT {
    @Autowired
    protected UserService userService;

    protected User createUser(TestInfo testName) {
        String username = testName.getDisplayName();
        String email = testName.getDisplayName() + "@devopsbuddy.com";

        Set<UserRole> userRoles = new HashSet<>();
        User basicUser = UserUtils.createBasicUser(username, email);
        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

        return userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
    }
}