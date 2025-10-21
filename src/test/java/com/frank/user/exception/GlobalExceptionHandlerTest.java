package com.frank.user.exception;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void handleNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Test resource not found");
        Map<String, String> response = handler.handleNotFound(ex);
        assertEquals("Test resource not found", response.get("error"));
    }

    @Test
    public void handleNotFound_NullMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException(null);
        Map<String, String> response = handler.handleNotFound(ex);
        assertEquals("Resource not found", response.get("error"));
    }

    @Test
    public void handleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        org.springframework.validation.BindingResult bindingResult = mock(org.springframework.validation.BindingResult.class);
        List<FieldError> errors = new ArrayList<>();
        FieldError error = new FieldError("user", "firstName", "Must not be blank");
        errors.add(error);
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(errors);

        Map<String, String> response = handler.handleValidationExceptions(ex);
        assertEquals("Must not be blank", response.get("firstName"));
    }

    @Test
    public void handleMethodArgumentTypeMismatch() {
        org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex =
                new org.springframework.web.method.annotation.MethodArgumentTypeMismatchException(
                        1, Long.class, "userId", null, new IllegalArgumentException("bad type"));
        Map<String, String> response = handler.handleTypeMismatch(ex);
        // response key is parameter name
        assertEquals("userId", response.keySet().iterator().next());
    }

    @Test
    public void handleConstraintViolationException() {
        jakarta.validation.ConstraintViolationException ex = mock(jakarta.validation.ConstraintViolationException.class);
        java.util.Set<jakarta.validation.ConstraintViolation<?>> violations = new java.util.HashSet<>();
        jakarta.validation.ConstraintViolation<?> v = mock(jakarta.validation.ConstraintViolation.class);
        jakarta.validation.Path path = mock(jakarta.validation.Path.class);
        jakarta.validation.Path.Node node = mock(jakarta.validation.Path.Node.class);
        when(node.getName()).thenReturn("userId");
        java.util.Iterator<jakarta.validation.Path.Node> it = java.util.Arrays.asList(node).iterator();
        when(path.iterator()).thenReturn(it);
        when(v.getPropertyPath()).thenReturn(path);
        when(v.getMessage()).thenReturn("must be > 0");
        violations.add(v);
        when(ex.getConstraintViolations()).thenReturn(violations);

        Map<String, String> response = handler.handleConstraintViolation(ex);
        // the handler stores propertyPath.toString() as key; ensure message present
        assertEquals("must be > 0", response.values().iterator().next());
    }
}