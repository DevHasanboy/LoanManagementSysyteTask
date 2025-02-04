package com.example.file.task.service.impl;

import com.example.file.task.entity.Accounts;
import com.example.file.task.entity.Transactions;
import com.example.file.task.enums.TransactionType;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.repository.AccountRepository;
import com.example.file.task.repository.TransactionRepository;
import com.example.file.task.request.TransactionRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.response.TransactionResponse;
import com.example.file.task.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public ApiResponse<?> create(TransactionRequest request) {
        Transactions transactions = new Transactions();
        transactions.setAmount(request.getAmount());
        if (TransactionType.DEPOSIT == request.getTransactionType()) {
            Accounts account = this.accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            double balance = account.getBalance() + request.getAmount();
            account.setBalance(balance);
            transactions.setAccounts(account);
        } else if (TransactionType.WITHDRAWAL == request.getTransactionType()) {
            Accounts account = this.accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            if (account.getBalance() > request.getAmount()) {
                double balance = account.getBalance() - request.getAmount();
                account.setBalance(balance);
            }
            transactions.setAccounts(account);
        }
        transactions.setTransactionType(request.getTransactionType());

        transactionRepository.save(transactions);

        return ApiResponse.builder()
                .success(true)
                .message("Transactions created successfully")
                .httpStatus(HttpStatus.OK)
                .build();

    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Transactions transactions = this.transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        TransactionResponse response = new TransactionResponse();
        response.setId(transactions.getId());
        response.setAmount(transactions.getAmount());
        response.setTransactionType(transactions.getTransactionType());
        if (transactions.getAccounts() != null) {
            Accounts account = this.accountRepository.findById(transactions.getAccounts().getId()).orElseThrow(()
                    -> new ResourceNotFoundException("Account not found"));
            response.setAccountId(account.getId());
        }
        return ApiResponse.builder()
                .success(true)
                .message("ok")
                .httpStatus(HttpStatus.OK)
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<?> updateById(TransactionRequest request, Long id) {
        Transactions transactions = this.transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (request == null) {
            return null;
        }
        Accounts account = this.accountRepository.findById(transactions.getAccounts().getId()).orElseThrow(()
                -> new ResourceNotFoundException("Account not found"));
        if (TransactionType.WITHDRAWAL == request.getTransactionType()) {
            if (account.getBalance() > request.getAmount()) {
                double balance = account.getBalance() - request.getAmount();
                account.setBalance(balance);
            }
            transactions.setAccounts(account);
        } else if (TransactionType.DEPOSIT == request.getTransactionType()) {
            if (account.getBalance() > request.getAmount()) {
                double balance = account.getBalance() - request.getAmount();
                account.setBalance(balance);
                transactions.setAccounts(account);
            }
        }
        transactions.setTransactionType(request.getTransactionType());

        if (request.getAccountId() != null) {
            Accounts acc = this.accountRepository.findById(request.getAccountId()).orElseThrow(()
                    -> new ResourceNotFoundException("Account not found"));
            transactions.setAccounts(acc);
        }
        if (request.getAmount() != null) {
            transactions.setAmount(request.getAmount());
        }
        this.transactionRepository.save(transactions);
        return ApiResponse.builder()
                .success(true)
                .message("Transactions updated successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        Transactions transactions = this.transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        this.transactionRepository.delete(transactions);
        return ApiResponse.builder()
                .success(true)
                .message("Transaction delete successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
