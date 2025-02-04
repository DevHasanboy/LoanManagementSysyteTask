package com.example.file.task.controller;

import com.example.file.task.auth.AuthService;
import com.example.file.task.request.LoginRequest;
import com.example.file.task.request.UserRequest;
import com.example.file.task.response.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@OpenAPIDefinition(tags = @Tag(name = "Auth", description = " Authlarni boshqarish Api si"))
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "rgistratsiya qilish jarayoni")
    @PostMapping("/register")
    @PreAuthorize("hasRole('MANAGER')")
    public ApiResponse<?> registerUser(@RequestBody @Valid UserRequest request) {
        return authService.registerUser(request);
    }

    @Operation(summary = "Login qilish jarayoni")
    @PostMapping("/login")
    public ApiResponse<?> loginUser(@RequestBody LoginRequest request) {
        return authService.loginUser(request);
    }
}
