package com.frank.user.jpa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressDabTest {

    @Test
    public void gettersAndSetters() {
        AddressDab a = new AddressDab();
        a.setStreet("123 Main St");
        a.setCity("Brisbane");
        a.setState("QLD");
        a.setPostCode("4000");

        assertEquals("123 Main St", a.getStreet());
        assertEquals("Brisbane", a.getCity());
        assertEquals("QLD", a.getState());
        assertEquals("4000", a.getPostCode());

        assertNotNull(a.toString());
    }
}
