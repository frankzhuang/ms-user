package com.frank.user.service;

import com.frank.user.jpa.UserDab;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceFallbackTest {

    @Test
    public void circuitBreakerFallBack_returnsPlaceholderUser() throws Exception {
        UserService svc = new UserService();

        Method m = UserService.class.getDeclaredMethod("circuitBreakerFallBack", Exception.class);
        m.setAccessible(true);
        UserDab result = (UserDab) m.invoke(svc, new RuntimeException("boom"));

        assertEquals("DB", result.getFirstName());
        assertEquals("CircuitBreaker", result.getLastName());
    }
}
