package com.example.file.task.test;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.entity.Accounts;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.mapper.AccountMapper;
import com.example.file.task.repository.AccountRepository;
import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.AccountResponse;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.impl.AccountServiceImpl;
import com.example.file.task.validation.AccountValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountTest {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private AccountValidation accountValidation;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setObject() {
        accountMapper = Mockito.mock(AccountMapper.class);
        accountRepository = Mockito.mock(AccountRepository.class);
        accountValidation = Mockito.mock(AccountValidation.class);
        accountService = new AccountServiceImpl(accountRepository, accountMapper, accountValidation);
    }

    @Test
    void createAccountPositive() {
        // Given: Test uchun input request
        AccountRequest request = new AccountRequest();
        request.setBalance(12.12);
        request.setClientId(1l);

        Accounts accountEntity = new Accounts();
        accountEntity.setBalance(11.11);
        accountEntity.setId(1l);

        // Mock: Validation bo‘sh ro‘yxat qaytarsin (no errors)
        when(accountValidation.validate(request)).thenReturn(Collections.emptyList());

        // Mock: AccountMapper toEntity() metodini chaqirganda, entity qaytarsin
        when(accountMapper.toEntity(request)).thenReturn(accountEntity);

        // When: `create` metodini chaqirish
        ApiResponse<?> response = accountService.create(request);

        // Then: Tekshirish
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Account created successfully", response.getMessage());
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());

        // `accountRepository.save()` chaqirilganligini tekshirish
        verify(accountRepository, times(1)).save(accountEntity);
    }

    @Test
    void createAccountNegative() {
        AccountRequest request = new AccountRequest();
        request.setBalance(12.12);
        request.setClientId(1l);
        List<ErrorDto> errors = List.of(new ErrorDto("field", "Invalid value"));
        when(accountValidation.validate(request)).thenReturn(errors);

        ApiResponse<?> response = accountService.create(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
        assertEquals("Validation Failed", response.getMessage());
        assertFalse(response.isSuccess());
        verify(accountValidation, times(1)).validate(request);
    }

    @Test
    void getAccountPositive() {
        // Given: Test uchun id va hisob
        Long id = 1L;
        Accounts account = new Accounts();
        account.setId(id);

        AccountResponse response = new AccountResponse();
        response.setClientId(1l);

        // Mock: findById() mavjud hisobni qaytarsin
        when(accountRepository.findById(1l)).thenReturn(Optional.of(account));

        // Mock: accountMapper.toResponse() to‘g‘ri response obyektini qaytarsin
        when(accountMapper.toResponse(account)).thenReturn(response);

        // When: getById metodini chaqirish
        ApiResponse<?> result = accountService.getById(id);

        // Then: Natijani tekshirish
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Account By id successfully", result.getMessage());
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertNotNull(result.getData());
        assertEquals(response, result.getData());

        // `findById` metodining chaqirilganligini tekshirish
        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    void getAccountNegative() {
        Long id = 99L;

        // Mock: findById() bo‘sh Optional qaytarsin
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then: getById chaqirilganda ResourceNotFoundException kutish
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> accountService.getById(id));

        assertEquals("Account not found", exception.getMessage());

        // `findById` metodining chaqirilganligini tekshirish
        verify(accountRepository, times(1)).findById(id);
    }
}
