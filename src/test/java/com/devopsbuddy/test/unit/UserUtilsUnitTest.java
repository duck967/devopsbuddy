package com.devopsbuddy.test.unit;

import com.devopsbuddy.utils.UserUtils;
import com.devopsbuddy.web.controllers.ForgotMyPasswordController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

/**
 * Created by tedonema on 15/04/2016.
 */
public class UserUtilsUnitTest {

    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    public void init() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    public void testPasswordResetEmailUrlConstruction() throws Exception {

        mockHttpServletRequest.setServerPort(8080); //Default is 80

        String token = UUID.randomUUID().toString();
        long userId = 123456;

        String expectedUrl = "http://localhost:8080" +
                ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" + userId + "&token=" + token;

        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId, token);

        assertEquals(expectedUrl, actualUrl);

    }
}