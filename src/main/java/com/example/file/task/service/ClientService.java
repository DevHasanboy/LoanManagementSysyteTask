package com.example.file.task.service;

import com.example.file.task.request.UserRequest;
import com.example.file.task.response.ApiResponse;

public interface ClientService {

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(UserRequest request, Long id);

    ApiResponse<?> deleteById(Long id);

    ApiResponse<?> findAll();
}
