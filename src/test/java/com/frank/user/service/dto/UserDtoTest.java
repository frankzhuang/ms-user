package com.frank.user.service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDtoTest {

    @Test
    public void gettersSettersEqualsHashCode() {
        Address addr = new Address();
        addr.setCity("Perth");
        addr.setStreet("5 Ocean Rd");
        addr.setState("WA");
        addr.setPostCode("6000");

        User u1 = new User();
        u1.setTitle("DR");
        u1.setFirstName("Alice");
        u1.setLastName("Wonder");
        u1.setGender("F");
        u1.setAddress(addr);

        User u2 = new User();
        u2.setTitle("DR");
        u2.setFirstName("Alice");
        u2.setLastName("Wonder");
        u2.setGender("F");
        u2.setAddress(addr);

        assertEquals("Alice", u1.getFirstName());
        assertEquals("Wonder", u1.getLastName());
        assertEquals("DR", u1.getTitle());
        assertEquals(addr, u1.getAddress());

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotNull(u1.toString());
    }
}
