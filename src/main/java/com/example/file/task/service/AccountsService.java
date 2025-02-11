package com.example.file.task.service;

import com.example.file.task.filter.AccountFilter;
import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.AccountResponse;
import com.example.file.task.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface AccountsService {

    ApiResponse<?> create(AccountRequest request);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(Long id, AccountRequest request);

    ApiResponse<?> deleteById(Long id);

    ApiResponse<?> findAll();

    ApiResponse<List<AccountResponse>> getAll(Pageable pageable, AccountFilter filter);

}
