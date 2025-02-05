package com.example.file.task.response;

import com.example.file.task.enums.AccountType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Long clientId;
    private AccountType accountType;
    private Double balance;
    private Double interestRate;
}
