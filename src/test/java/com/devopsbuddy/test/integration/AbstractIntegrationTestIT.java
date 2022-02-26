package com.devopsbuddy.test.integration;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;
import com.devopsbuddy.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tedonema on 10/04/2016.
 */
public abstract class AbstractIntegrationTestIT {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;


    protected Plan createPlan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }

    // private Role createRole(RolesEnum rolesEnum) {
    //     return new Role(rolesEnum);
    // }

    // private Plan createBasicPlan() {
    //     Plan plan = new Plan(); 
    //     plan.setId(BASIC_PLAN_ID); 
    //     plan.setName("Basic"); 
    //     return plan; 
    // } 
    
    protected Role createRole(RolesEnum rolesEnum) {
        // Role role = new Role(); 
        // role.setId(BASIC_ROLE_ID); 
        // role.setName("ROLE_USER"); 
        // return role; 
        return new Role(rolesEnum);
    }
    
    protected User createUser(String username, String email) {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(username, email);
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);
        return basicUser;
    }

    protected User createUser(TestInfo testName) {
        return createUser(testName.getDisplayName(), testName.getDisplayName() + "@devopsbuddy.com");
    }

}