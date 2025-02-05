package com.example.file.task.test;

import com.example.file.task.mapper.AccountMapper;
import com.example.file.task.repository.AccountRepository;
import com.example.file.task.service.impl.AccountServiceImpl;
import com.example.file.task.validation.AccountValidation;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

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
}
