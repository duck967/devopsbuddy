package com.devopsbuddy.test.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.devopsbuddy.DevopsbuddyApplication;
import com.devopsbuddy.backend.persistence.domain.backend.PasswordResetToken;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.service.PasswordResetTokenService;
import com.devopsbuddy.backend.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tedonema on 10/04/2016.
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevopsbuddyApplication.class)
public class PasswordResetTokenServiceIntegrationTestIT extends AbstractServiceIntegrationTestIT {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Test
    public void testCreateNewTokenForUserEmail(TestInfo testName) throws Exception {

        User user = createUser(testName);

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        assertNotNull(passwordResetToken);
        assertNotNull(passwordResetToken.getToken());

    }

    @Test
    public void testFindByToken(TestInfo testName) throws Exception {
        User user = createUser(testName);

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        assertNotNull(passwordResetToken);
        assertNotNull(passwordResetToken.getToken());

        PasswordResetToken token = passwordResetTokenService.findByToken(passwordResetToken.getToken());
        assertNotNull(token);

    }

}