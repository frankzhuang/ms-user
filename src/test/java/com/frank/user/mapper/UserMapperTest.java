package com.frank.user.mapper;

import com.frank.user.jpa.AddressDab;
import com.frank.user.jpa.UserDab;
import com.frank.user.service.dto.Address;
import com.frank.user.service.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {


    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User user;
    Address address;
    UserDab userDab;
    AddressDab addressDab;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setCity("Sydney");
        address.setStreet("123 Pete St");
        address.setPostCode("2000");
        address.setState("NSW");

        addressDab = new AddressDab();
        addressDab.setCity("Mel");
        addressDab.setStreet("111 St");
        addressDab.setPostCode("3000");
        addressDab.setState("VIC");

        user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setTitle("MR");
        user.setGender("M");
        //user.setAddress(address);

        userDab = new UserDab();
        userDab.setFirstName("John");
        userDab.setLastName("Smith");
        userDab.setTitle("MR");
        userDab.setGender("M");
        //userDab.setAddress(addressDab);

    }

    @Test
    void givenUserToUserDab_whenMaps_thenCorrect() {
        UserDab userDab1 = userMapper.map(user);

        assertEquals(userDab1.getFirstName(), user.getFirstName());
        assertEquals(userDab1.getLastName(), user.getLastName());
        assertEquals(userDab1.getGender(), user.getGender());
        assertEquals(userDab1.getTitle(), user.getTitle());
    }

    @Test
    void givenUserDabToUser_whenMaps_thenCorrect() {
        User user1 = userMapper.map(userDab);

        assertEquals(userDab.getLastName(), user1.getLastName());
        assertEquals(userDab.getFirstName(), user1.getFirstName());
        assertEquals(userDab.getGender(), user1.getGender());
        assertEquals(userDab.getTitle(), user1.getTitle());
    }

    @Test
    void givenAddressToAddressDab_whenMaps_thenCorrect() {
        AddressDab addressDab1 = userMapper.map(address);

        assertEquals(addressDab1.getCity(), address.getCity());
        assertEquals(addressDab1.getStreet(), address.getStreet());
        assertEquals(addressDab1.getState(), address.getState());
        assertEquals(addressDab1.getPostCode(), address.getPostCode());
    }

    @Test
    void givenAddressDabToAddress_whenMaps_thenCorrect() {
        Address address1 = userMapper.map(addressDab);

        assertEquals(address1.getCity(), addressDab.getCity());
        assertEquals(address1.getStreet(), addressDab.getStreet());
        assertEquals(address1.getState(), addressDab.getState());
        assertEquals(address1.getPostCode(), addressDab.getPostCode());
    }
}