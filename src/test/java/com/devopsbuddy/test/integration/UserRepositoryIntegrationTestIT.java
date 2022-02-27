package com.devopsbuddy.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.devopsbuddy.DevopsbuddyApplication;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)

// @WebAppConfiguration
// @DataJpaTest
public class UserRepositoryIntegrationTestIT extends AbstractIntegrationTestIT {
    
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    private static final int BASIC_PLAN_ID = 1;
    private static final int BASIC_ROLE_ID = 1;

    @BeforeEach
    public void init() {

        assertNotNull(planRepository);
        assertNotNull(roleRepository);
        assertNotNull(userRepository);
    }

    
    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId()).orElse(null);
        assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() throws Exception {

        Role userRole  = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId()).orElse(null);
        assertNotNull(retrievedRole);
    }

    // @Test
    // public void createNewUserOld() throws Exception {

    //     Plan basicPlan = createPlan(PlansEnum.BASIC);
    //     planRepository.save(basicPlan);

    //     User basicUser = UserUtils.createBasicUser();
    //     basicUser.setPlan(basicPlan);

    //     Role basicRole = createRole(RolesEnum.BASIC);
    //     Set<UserRole> userRoles = new HashSet<>();
    //     UserRole userRole = new UserRole(basicUser, basicRole);
    //     userRoles.add(userRole);

    //     basicUser.getUserRoles().addAll(userRoles);

    //     for (UserRole ur : userRoles) {
    //         roleRepository.save(ur.getRole());
    //     }

    //     basicUser = userRepository.save(basicUser);
    //     User newlyCreatedUser = userRepository.findById(basicUser.getId()).orElse(null);
    //     assertNotNull(newlyCreatedUser);
    //     assertTrue(newlyCreatedUser.getId() != 0);
    //     assertNotNull(newlyCreatedUser.getPlan());
    //     assertNotNull(newlyCreatedUser.getPlan().getId());
    //     Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
    //     for (UserRole ur : newlyCreatedUserUserRoles) {
    //         assertNotNull(ur.getRole());
    //         assertNotNull(ur.getRole().getId());
    //     }

    // }

    @Test
    public void createNewUser(TestInfo testInfo) throws Exception {

        String username = testInfo.getDisplayName();
        String email = testInfo.getDisplayName() + "@example.com";

        User basicUser = createUser(username, email);

        User newlyCreatedUser = userRepository.findById(basicUser.getId()).orElse(null);
        assertNotNull(newlyCreatedUser);
        assertTrue(newlyCreatedUser.getId() != 0);
        assertNotNull(newlyCreatedUser.getPlan());
        assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            assertNotNull(ur.getRole());
            assertNotNull(ur.getRole().getId());
        }

    }

    
    @Test
    public void testDeleteUser(TestInfo testInfo) throws Exception {

        String username = testInfo.getDisplayName();
        String email = testInfo.getDisplayName() + "@example.com";

        User basicUser = createUser(username, email);
        userRepository.deleteById(basicUser.getId());
    }


    @Test
    public void testGetUserByEmail(TestInfo testInfo) throws Exception {

        User user = createUser(testInfo);

        User newlyFoundUser = userRepository.findByEmail(user.getEmail());
        assertNotNull(newlyFoundUser);
        assertNotNull(newlyFoundUser.getId());
    }

    @Test
    public void testUpdateUserPassword(TestInfo testInfo) throws Exception {
        User user = createUser(testInfo);
        assertNotNull(user);
        assertNotNull(user.getId());

        String newPassword = UUID.randomUUID().toString();

        userRepository.updateUserPassword(user.getId(), newPassword);

        user = userRepository.findById(user.getId()).orElse(null);
        assertEquals(newPassword, user.getPassword());

    }
    //-----------------> Private methods


}
