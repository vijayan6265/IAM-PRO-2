package com.zaalima.iam_server.service;

import com.zaalima.iam_server.entity.Authority;
import com.zaalima.iam_server.entity.Role;
import com.zaalima.iam_server.entity.User;
import com.zaalima.iam_server.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {

            // Add role
            authorities.add(
                    new SimpleGrantedAuthority(role.getName())
            );

            // Add permissions
            for (Authority authority : role.getAuthorities()) {

                authorities.add(
                        new SimpleGrantedAuthority(
                                authority.getName()
                        )
                );
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .authorities(authorities)
                .build();
    }
}