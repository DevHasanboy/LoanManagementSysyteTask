package com.example.file.task.auth;

import com.example.file.task.request.LoginRequest;
import com.example.file.task.request.UserRequest;
import com.example.file.task.response.ApiResponse;

public interface AuthService {
    ApiResponse<?> registerUser(UserRequest request);

    ApiResponse<?> loginUser(LoginRequest request);
}
