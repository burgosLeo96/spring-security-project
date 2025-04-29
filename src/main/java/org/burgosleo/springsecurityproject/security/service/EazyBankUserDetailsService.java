package org.burgosleo.springsecurityproject.security.service;

import lombok.RequiredArgsConstructor;
import org.burgosleo.springsecurityproject.entity.Customer;
import org.burgosleo.springsecurityproject.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EazyBankUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return customerRepository
                .findByEmail(username)
            .map(EazyBankUserDetailsService::convertToUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("User details not found for user: " + username));
    }

    private static UserDetails convertToUserDetails(Customer customer) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(customer.getRole());
        return new User(customer.getEmail(), customer.getPassword(), List.of(grantedAuthority));
    }
}
