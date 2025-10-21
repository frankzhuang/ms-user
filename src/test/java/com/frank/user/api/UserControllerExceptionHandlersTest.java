package com.frank.user.api;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerExceptionHandlersTest {

    private UserController controller = new UserController();

    @Test
    public void handleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        org.springframework.validation.BindingResult bindingResult = mock(org.springframework.validation.BindingResult.class);
        List<FieldError> errors = new ArrayList<>();
        FieldError error = new FieldError("user", "firstName", "Must not be blank");
        errors.add(error);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>(errors));

        Map<String, String> response = controller.handleValidationExceptions(ex);
        assertEquals("Must not be blank", response.get("firstName"));
    }

    @Test
    public void handleTypeMismatch() {
        org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex =
                new org.springframework.web.method.annotation.MethodArgumentTypeMismatchException(
                        1, Long.class, "userId", null, new IllegalArgumentException("bad type"));

        Map<String, String> response = controller.handleValidationExceptions(ex);
        assertEquals("userId", response.keySet().iterator().next());
    }

    @Test
    public void handleConstraintViolation() {
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

        Map<String, String> response = controller.handleValidationExceptions(ex);
        assertEquals("must be > 0", response.values().iterator().next());
    }
}
