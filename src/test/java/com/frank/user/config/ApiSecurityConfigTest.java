package com.frank.user.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiSecurityConfigTest {

    @Test
    public void usersBean_containsDevAndAdmin() {
        ApiSecurityConfig cfg = new ApiSecurityConfig();

        UserDetailsService uds = cfg.users("devuser", "devpass", "adminuser", "adminpass");

        UserDetails dev = uds.loadUserByUsername("devuser");
        UserDetails admin = uds.loadUserByUsername("adminuser");

        assertNotNull(dev);
        assertNotNull(admin);
        assertTrue(dev.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("USER")));
        assertTrue(admin.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("ADMIN")));
    }
}
