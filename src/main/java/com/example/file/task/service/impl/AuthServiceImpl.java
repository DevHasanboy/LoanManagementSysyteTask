package com.example.file.task.service.impl;

import com.example.file.task.auth.AuthService;
import com.example.file.task.dto.ErrorDto;
import com.example.file.task.entity.User;
import com.example.file.task.entity.UserRole;
import com.example.file.task.exception.ApiException;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.repository.RoleRepository;
import com.example.file.task.repository.TokenRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.LoginRequest;
import com.example.file.task.request.UserRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.response.AuthResponse;
import com.example.file.task.utils.JwtUtil;
import com.example.file.task.validation.UserValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager,
                           UserValidation userValidation,
                           UserDetailsServiceImpl userDetailsService,
                           RoleRepository roleRepository,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userValidation = userValidation;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public ApiResponse<?> registerUser(UserRequest request) {
        List<ErrorDto> errors = this.userValidation.validate(request);
        if (!errors.isEmpty()) {
            return ApiResponse.builder()
                    .message("Validation Failed")
                    .errorsList(errors)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        //  user.setRoleObj(Role.CLIENT);
        UserRole role = this.roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            user.getRole().add(role);
        } else {
            user.setRole(new HashSet<>(Set.of(role)));
        }
        this.userRepository.save(user);
        return ApiResponse.builder()
                .success(true)
                .message("User registered successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<?> loginUser(LoginRequest request) {

        User user = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword());
            authenticationManager.authenticate(
                    authentication);
        } catch (RuntimeException e) {
            new ApiException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        jwtUtil.generateToken(userDetails.getUsername());

        return ApiResponse.builder()
                .success(true)
                .message("Login successfully")
                .httpStatus(HttpStatus.OK)
                .data(AuthResponse.builder()
                        .userId(user.getId())
                        .fullName(user.getFirstName() + " " + user.getLastName())
                        .username(user.getUsername())
                        .build())
                .build();
    }

}
