package com.frank.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not here");
        assertEquals("Not here", ex.getMessage());
    }

    @Test
    public void testNullMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException(null);
        assertEquals(null, ex.getMessage());
    }
}
