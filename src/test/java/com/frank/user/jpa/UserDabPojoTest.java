package com.frank.user.jpa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDabPojoTest {

    @Test
    public void gettersSettersAndEqualsHashCode() {
        UserDab u1 = new UserDab();
        u1.setUserId(1L);
        u1.setFirstName("Alice");
        u1.setLastName("Smith");
        u1.setTitle("MS");

        UserDab u2 = new UserDab();
        u2.setUserId(1L);
        u2.setFirstName("Alice");
        u2.setLastName("Smith");
        u2.setTitle("MS");

        assertEquals(u1.getUserId(), u2.getUserId());
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotNull(u1.toString());
    }

    @Test
    public void addressEmbedding() {
        UserDab u = new UserDab();
        AddressDab a = new AddressDab();
        a.setCity("Perth");
        a.setStreet("1 Ocean Rd");
        u.setAddress(a);

        assertNotNull(u.getAddress());
        assertEquals("Perth", u.getAddress().getCity());
        assertEquals("1 Ocean Rd", u.getAddress().getStreet());
    }
}
