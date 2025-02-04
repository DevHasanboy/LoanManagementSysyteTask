package com.example.file.task.request;

import com.example.file.task.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private TransactionType transactionType;
    private Double amount;
    private Long accountId;

}
