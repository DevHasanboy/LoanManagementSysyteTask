package com.example.file.task.service;

import com.example.file.task.request.ClientRequest;
import com.example.file.task.response.ApiResponse;

public interface ClientService {

    ApiResponse<?> create(ClientRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(ClientRequest request, Long id);

    ApiResponse<?> deleteById(Long id);
}
