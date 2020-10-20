package com.frank.user.service;

import com.frank.user.jpa.UserDab;
import com.frank.user.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @CircuitBreaker(name = "mainService", fallbackMethod="circuitBreakerFallBack")
    @Transactional
    public UserDab getUser(Long userId) {
        log.debug("userId={}", userId);
        UserDab userDab = userRepository.getOne(userId);
        log.debug("getUser End");
        
        return userDab;
    }

    @CircuitBreaker(name = "mainService", fallbackMethod="circuitBreakerFallBack")
    @Transactional
    public UserDab saveUser(UserDab user) {
        log.debug("userId={}, firstName={}, lastName={}", user.getUserId(), user.getFirstName(), user.getLastName());

        UserDab userDab = userRepository.saveAndFlush(user);

        log.debug("saveUer End");

        return userDab;
    }

    private  UserDab circuitBreakerFallBack(Exception e){
        log.debug("circuitBreakerFallBack--getUser circuitBreakerFallBack Start");

        UserDab userDab = new UserDab();
        userDab.setTitle("MR");
        userDab.setFirstName("DB");
        userDab.setLastName("CircuitBreaker");

        log.debug("circuitBreakerFallBack--getUser circuitBreakerFallBack END");
        return userDab;
    }

}
