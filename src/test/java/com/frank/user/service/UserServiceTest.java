package com.frank.user.service;

import com.frank.user.exception.ResourceNotFoundException;
import com.frank.user.jpa.UserDab;
import com.frank.user.jpa.AddressDab;
import com.frank.user.jpa.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    UserDab userDab;
    AddressDab addressDab;

    @BeforeEach
    public void setUp() throws Exception {

        addressDab = new AddressDab();
        addressDab.setCity("Mel");
        addressDab.setStreet("111 St");

        userDab = new UserDab();
        userDab.setFirstName("John");
        userDab.setLastName("Smith");
        userDab.setAddress(addressDab);

    }

    @Test
    public void getUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userDab));

        UserDab userDab1 = userService.getUser(1L);
        verify(userRepository).findById(1L);

        assertEquals(userDab1.getFirstName(), userDab.getFirstName());
        assertEquals(userDab1.getLastName(), userDab.getLastName());
        assertEquals(userDab1.getAddress().getCity(), userDab.getAddress().getCity());
        assertEquals(userDab1.getAddress().getStreet(), userDab.getAddress().getStreet());
    }

    @Test
    public void getUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            userService.getUser(1L);
        } catch (ResourceNotFoundException e) {
            assertEquals("User not found: 1", e.getMessage());
        }
        verify(userRepository).findById(1L);
    }

    @Test
    public void saveUser() {
        when(userRepository.saveAndFlush(any(UserDab.class))).thenReturn(userDab);

        UserDab userDab1 = userService.saveUser(userDab);
        verify(userRepository).saveAndFlush(any(UserDab.class));

        assertEquals(userDab1.getFirstName(), userDab.getFirstName());
        assertEquals(userDab1.getLastName(), userDab.getLastName());
        assertEquals(userDab1.getAddress().getCity(), userDab.getAddress().getCity());
        assertEquals(userDab1.getAddress().getStreet(), userDab.getAddress().getStreet());
    }
}