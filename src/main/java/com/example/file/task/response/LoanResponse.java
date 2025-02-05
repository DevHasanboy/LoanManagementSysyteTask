package com.example.file.task.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    private Long id;
    private String loanName;
    private Double loanAmount;
    private Long userId;
}
