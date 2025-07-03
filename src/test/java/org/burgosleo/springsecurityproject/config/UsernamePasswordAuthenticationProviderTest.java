package org.burgosleo.springsecurityproject.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsernamePasswordAuthenticationProviderTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UsernamePasswordAuthenticationProvider authenticationProvider;

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @BeforeEach
    void setUp() {
        authenticationProvider = new UsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Test
    void testAuthenticateWithValidCredentials() {
        // Arrange
        UserDetails userDetails = new User(USERNAME, ENCODED_PASSWORD, 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);

        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);

        // Act
        Authentication result = authenticationProvider.authenticate(authentication);

        // Assert
        assertNotNull(result);
        assertTrue(result.isAuthenticated());
        assertEquals(userDetails, result.getPrincipal());
        assertEquals(PASSWORD, result.getCredentials());
        assertTrue(result.getAuthorities().containsAll(userDetails.getAuthorities()));
        assertTrue(userDetails.getAuthorities().containsAll(result.getAuthorities()));

        verify(userDetailsService).loadUserByUsername(USERNAME);
        verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD);
    }

    @Test
    void testAuthenticateWithInvalidCredentials() {
        // Arrange
        UserDetails userDetails = new User(USERNAME, ENCODED_PASSWORD, 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);

        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            authenticationProvider.authenticate(authentication);
        });

        verify(userDetailsService).loadUserByUsername(USERNAME);
        verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD);
    }

    @Test
    void testSupportsWithValidAuthentication() {
        // Act
        boolean result = authenticationProvider.supports(UsernamePasswordAuthenticationToken.class);

        // Assert
        assertTrue(result);
    }

    @Test
    void testSupportsWithInvalidAuthentication() {
        // Act
        boolean result = authenticationProvider.supports(Authentication.class);

        // Assert
        assertFalse(result);
    }
}
