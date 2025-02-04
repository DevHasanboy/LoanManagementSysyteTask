package com.example.file.task.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    @NotBlank(message = "loanName cannot be null or empty")
    private String loanName;
    private Double loanAmount;
    private Long clientId;
}
