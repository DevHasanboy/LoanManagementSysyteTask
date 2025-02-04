package com.example.file.task.service;

import com.example.file.task.request.TransactionRequest;
import com.example.file.task.response.ApiResponse;

public interface TransactionService {

    ApiResponse<?> create(TransactionRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(TransactionRequest request,Long id);

    ApiResponse<?> deleteById(Long id);
}
