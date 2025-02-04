package com.example.file.task.service.impl;

import com.example.file.task.exception.UserNotFoundException;
import com.example.file.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("USER NOT FOUND"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("MANAGER")
                .build();
    }
}
