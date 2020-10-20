package com.frank.user.service;

import com.frank.user.jpa.UserDab;
import com.frank.user.jpa.AddressDab;
import com.frank.user.jpa.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    UserDab userDab;
    AddressDab addressDab;

    @Before
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

        assertEquals(userDab1.getFirstName(), userDab.getFirstName());
        assertEquals(userDab1.getLastName(), userDab.getLastName());
        assertEquals(userDab1.getAddress().getCity(), userDab.getAddress().getCity());
        assertEquals(userDab1.getAddress().getStreet(), userDab.getAddress().getStreet());

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