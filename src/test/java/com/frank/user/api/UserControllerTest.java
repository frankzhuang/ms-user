package com.frank.user.api;

import com.frank.user.service.dto.User;
import com.frank.user.jpa.AddressDab;
import com.frank.user.jpa.UserDab;
import com.frank.user.mapper.UserMapper;
import com.frank.user.service.UserService;
import com.frank.user.service.dto.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    UserMapper userMapper;

    UserDab userDab;
    AddressDab addressDab;

    User user;
    Address address;

    @Before
    public void setUp() throws Exception {

        addressDab = new AddressDab();
        addressDab.setCity("Mel");
        addressDab.setStreet("111 St");

        userDab = new UserDab();
        userDab.setFirstName("John");
        userDab.setLastName("Smith");
        userDab.setAddress(addressDab);

        address = new Address();
        address.setCity("Sydney");
        address.setStreet("123 Pete St");

        user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setAddress(address);

    }

    @Test
    public void getUserDetails() {
        when(userService.getUser(1L)).thenReturn(userDab);
        when(userMapper.map(userDab)).thenReturn(user);

        User user1 = userController.getUserDetails(1l);
        verify(userService).getUser(1L);

        assertEquals(user1.getFirstName(), user.getFirstName());
        assertEquals(user1.getLastName(), user.getLastName());
        assertEquals(user1.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(user1.getAddress().getStreet(), user.getAddress().getStreet());

    }

    @Test
    public void createUserDetails() {
        when(userService.saveUser(userDab)).thenReturn(userDab);
        when(userMapper.map(user)).thenReturn(userDab);

        User user1 = userController.createUserDetails(user);
        verify(userService).saveUser(userDab);

        assertEquals(user1.getFirstName(), user.getFirstName());
        assertEquals(user1.getLastName(), user.getLastName());
        assertEquals(user1.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(user1.getAddress().getStreet(), user.getAddress().getStreet());

    }

    @Test
    public void updateUserDetail() {
        when(userService.saveUser(userDab)).thenReturn(userDab);
        when(userMapper.map(user)).thenReturn(userDab);

        User user1 = userController.updateUserDetail(1l, user);
        verify(userService).saveUser(userDab);

        assertEquals(user1.getFirstName(), user.getFirstName());
        assertEquals(user1.getLastName(), user.getLastName());
        assertEquals(user1.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(user1.getAddress().getStreet(), user.getAddress().getStreet());

    }
}