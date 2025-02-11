package com.example.file.task.filter;

import com.example.file.task.enums.AccountType;

public record AccountFilter(
        Long id,
        Long clientId,
        AccountType accountType
) {
}
