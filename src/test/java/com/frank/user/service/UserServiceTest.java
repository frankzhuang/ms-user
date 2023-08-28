package com.frank.user.service;

import com.frank.user.jpa.AddressDab;
import com.frank.user.jpa.UserDab;
import com.frank.user.jpa.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    public void getUser() {
        when(userRepository.getOne(1L)).thenReturn(userDab);

        UserDab userDab1 = userService.getUser(1l);
        verify(userRepository).getOne(1L);

        assertTrue(userDab1.getFirstName().equals(userDab.getFirstName()));
        assertTrue(userDab1.getLastName().equals(userDab.getLastName()));
        assertTrue(userDab1.getAddress().getCity().equals(userDab.getAddress().getCity()));
        assertTrue(userDab1.getAddress().getStreet().equals(userDab.getAddress().getStreet()));

    }

    @Test
    public void saveUser() {
        given(userRepository.saveAndFlush(any(UserDab.class))).willReturn(userDab);

        UserDab userDab1 = userService.saveUser(userDab);
        verify(userRepository).saveAndFlush(any(UserDab.class));

        assertTrue(userDab1.getFirstName().equals(userDab.getFirstName()));
        assertTrue(userDab1.getLastName().equals(userDab.getLastName()));
        assertTrue(userDab1.getAddress().getCity().equals(userDab.getAddress().getCity()));
        assertTrue(userDab1.getAddress().getStreet().equals(userDab.getAddress().getStreet()));
    }
}