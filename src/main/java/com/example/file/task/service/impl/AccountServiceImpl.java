package com.example.file.task.service.impl;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.entity.Accounts;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.filter.AccountFilter;
import com.example.file.task.mapper.AccountMapper;
import com.example.file.task.repository.AccountRepository;
import com.example.file.task.repository.specification.AccountSpecification;
import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.AccountResponse;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.AccountsService;
import com.example.file.task.validation.AccountValidation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountsService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountValidation accountValidation;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public ApiResponse<?> create(AccountRequest request) {

        List<ErrorDto> errorList = this.accountValidation.validate(request);
        if (!errorList.isEmpty()) {
            return ApiResponse.builder().message("Validation Failed").httpStatus(HttpStatus.BAD_REQUEST).errorsList(errorList).build();
        }

        Accounts entity = this.accountMapper.toEntity(request);
        this.accountRepository.save(entity);
        logger.info("Account created");
        return ApiResponse.builder().success(true).message("Account created successfully").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Accounts account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        AccountResponse response = this.accountMapper.toResponse(account);
        logger.info("Account found By Id :{}", response);
        return ApiResponse.builder().success(true).message("Account By id successfully").httpStatus(HttpStatus.OK).data(response).build();
    }

    @Override
    public ApiResponse<?> updateById(Long id, AccountRequest request) {
        Accounts account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        this.accountMapper.updateToEntityFromRequest(request, account);
        this.accountRepository.save(account);
        logger.info("Account updated By Id :{}", account);
        return ApiResponse.builder().success(true).message("Account updated successfully").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        Accounts account = this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        this.accountRepository.delete(account);
        logger.info("Account deleted By Id :{}", account);
        return ApiResponse.builder().success(true).message("Account deleted successfully").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ApiResponse<?> findAll() {
        List<Accounts> list = this.accountRepository.findAll();
        List<AccountResponse> result = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Accounts account : list) {
                AccountResponse response = this.accountMapper.toResponse(account);
                result.add(response);
            }
            return ApiResponse.builder().success(true).message("Account List").data(result).httpStatus(HttpStatus.OK).build();
        }
        return ApiResponse.builder().success(true).message("Account List").data(new ArrayList<>()).httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ApiResponse<List<AccountResponse>> getAll(Pageable pageable, AccountFilter filter) {
        Specification<Accounts> accountSpecification = new AccountSpecification(filter);
        Page<AccountResponse> responses = accountRepository.findAll(accountSpecification, pageable)
                .map(this.accountMapper::toResponse);

        ApiResponse<List<AccountResponse>> response = new ApiResponse<>(true,
                "Category data list",
                responses.getContent()
        );
        response.getMeta().put("total", responses.getTotalElements());
        response.getMeta().put("pages", responses.getTotalPages());
        return response;

    }

}
