package com.example.file.task.service.impl;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.entity.Accounts;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.mapper.AccountMapper;
import com.example.file.task.repository.AccountRepository;
import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.AccountResponse;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.AccountsService;
import com.example.file.task.validation.AccountValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountsService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountValidation accountValidation;

    @Override
    public ApiResponse<?> create(AccountRequest request) {

        List<ErrorDto> errorList = this.accountValidation.validate(request);
        if (!errorList.isEmpty()) {
            return ApiResponse.builder()
                    .message("Validation Failed")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorsList(errorList)
                    .build();
        }

        Accounts entity = this.accountMapper.toEntity(request);
        this.accountRepository.save(entity);
        return ApiResponse.builder()
                .success(true)
                .message("Account created successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Accounts account = this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        AccountResponse response = this.accountMapper.toResponse(account);
        return ApiResponse.builder()
                .success(true)
                .message("Account By id successfully")
                .httpStatus(HttpStatus.OK)
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<?> updateById(Long id, AccountRequest request) {
        Accounts account = this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        this.accountMapper.updateToEntityFromRequest(request, account);
        this.accountRepository.save(account);
        return ApiResponse.builder()
                .success(true)
                .message("Account updated successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        Accounts account = this.accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        this.accountRepository.delete(account);
        return ApiResponse.builder()
                .success(true)
                .message("Account deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }


}
