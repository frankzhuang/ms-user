package com.frank.user.service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressDtoTest {

    @Test
    public void gettersSetters() {
        Address a = new Address();
        a.setStreet("10 Downing St");
        a.setCity("London");
        a.setState("ENG");
        a.setPostCode("SW1A");

        assertEquals("10 Downing St", a.getStreet());
        assertEquals("London", a.getCity());
        assertEquals("ENG", a.getState());
        assertEquals("SW1A", a.getPostCode());
        assertEquals(a, a);
    }
}
