package com.frank.user.jpa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressDabPojoTest {

    @Test
    public void gettersSettersToString() {
        AddressDab a = new AddressDab();
        a.setCity("Brisbane");
        a.setState("QLD");
        a.setPostCode("4000");
        a.setStreet("10 King St");

        assertEquals("Brisbane", a.getCity());
        assertEquals("QLD", a.getState());
        assertEquals("4000", a.getPostCode());
        assertEquals("10 King St", a.getStreet());
        assertNotNull(a.toString());
    }
}
