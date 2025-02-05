package com.example.file.task.service;

import com.example.file.task.request.LoanRequest;
import com.example.file.task.response.ApiResponse;


public interface LoanService {

    ApiResponse<?> create(LoanRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(Long id, LoanRequest request);

    ApiResponse<?> deleteById(Long id);

    ApiResponse<?> findAll();
}
