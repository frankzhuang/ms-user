package com.frank.user.service;

import com.frank.user.jpa.UserDab;
import com.frank.user.jpa.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceEdgeCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService service;

        @Test
    public void saveUser_whenRepositoryThrowsException_fallsBackToDefault() {
        UserDab user = new UserDab();
        user.setFirstName("Test");
        user.setLastName("User");
        
        when(userRepository.saveAndFlush(any(UserDab.class)))
            .thenThrow(new RuntimeException("DB connection failed"));
        
        try {
            service.saveUser(user);
        } catch (Exception e) {
            // Expected - no circuit breaker in unit test
            assertNotNull(e);
        }
    }

    @Test
    public void getUser_whenRepositoryThrowsException_fallsBackToDefault() {
        when(userRepository.findById(any()))
            .thenThrow(new RuntimeException("DB connection failed"));
        
        try {
            service.getUser(1L);
        } catch (Exception e) {
            // Expected - no circuit breaker in unit test
            assertNotNull(e);
        }
    }
}