package com.frank.user.jpa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDabTest {

    @Test
    public void gettersAndSetters() {
        UserDab u = new UserDab();
        u.setFirstName("Jane");
        u.setLastName("Roe");
        u.setTitle("MS");

        assertEquals("Jane", u.getFirstName());
        assertEquals("Roe", u.getLastName());
        assertEquals("MS", u.getTitle());
    }
}
