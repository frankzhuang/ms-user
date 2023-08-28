package com.frank.user.api;

import com.frank.user.jpa.UserDab;
import com.frank.user.mapper.UserMapper;
import com.frank.user.service.UserService;
import com.frank.user.service.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/userdetails", produces = APPLICATION_JSON_VALUE)
@Validated
@Slf4j
public class UserController {
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    private UserService userService;
    private UserMapper userMapper;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User getUserDetails(@PathVariable("userId") @Min(value = 1, message = "UserId must be great than 0") Long userId) {
        log.debug("getUserDetails -- UserId={}", userId);

        UserDab userDab = userService.getUser(userId);
        User user = userMapper.map(userDab);

        log.debug("getUserDetails END");
        return user;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User createUserDetails(@Valid @RequestBody User user) {
        log.debug("getUserDetails -- firstname={}, lastName={}", user.getFirstName(), user.getLastName());

        UserDab userDab = userMapper.map(user);
        userService.saveUser(userDab);

        log.debug("createUserDetails END");
        return user;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User updateUserDetail(@PathVariable("userId") @Min(value = 1, message = "UserId must be great than 0") Long userId, @Valid @RequestBody User user) {
        log.debug("updateUserDetail -- userId={}, firstname={}, lastName={}", userId, user.getFirstName(), user.getLastName());

        UserDab userDab = userMapper.map(user);
        userDab.setUserId(userId);
        userService.saveUser(userDab);

        log.debug("updateUserDetail END");
        return user;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.debug("handleValidationExceptions MethodArgumentNotValidException Start");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.debug("handleValidationExceptions MethodArgumentNotValidException End");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        log.debug("handleValidationExceptions MethodArgumentTypeMismatchException Start");

        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getName(), ex.getName() + "'s type must be " + ex.getRequiredType().getSimpleName());

        log.debug("handleValidationExceptions MethodArgumentTypeMismatchException End");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(ConstraintViolationException ex) {
        log.debug("handleValidationExceptions ConstraintViolationException Start");

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error ->{
            errors.put(error.getPropertyPath().toString(), error.getMessage());
        });

        log.debug("handleValidationExceptions ConstraintViolationException End");
        return errors;
    }
}
