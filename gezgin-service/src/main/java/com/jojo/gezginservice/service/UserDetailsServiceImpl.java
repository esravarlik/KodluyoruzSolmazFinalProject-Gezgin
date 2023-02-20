package com.jojo.gezginservice.service;

import com.jojo.gezginservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        var roles = Stream.of(user.getRole())
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                roles);
    }
}
