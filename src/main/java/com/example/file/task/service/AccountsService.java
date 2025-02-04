package com.example.file.task.service;

import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.ApiResponse;

import java.math.BigDecimal;

public interface AccountsService {

    ApiResponse<?> create(AccountRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(Long id, AccountRequest request);

    ApiResponse<?> deleteById(Long id);


}
