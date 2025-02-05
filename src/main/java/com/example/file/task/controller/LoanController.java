package com.example.file.task.controller;

import com.example.file.task.request.LoanRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loan")

public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    @Operation(tags = "Create", summary = "Create new Loan")
    public ApiResponse<?> create(@RequestBody @Valid LoanRequest loanRequest) {
        return this.loanService.create(loanRequest);

    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}/get-by-id")
    @Operation(tags = "Get", summary = " Get Loan By Id")
    public ApiResponse<?> getById(@PathVariable("id") Long id) {
        return this.loanService.getById(id);

    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/update-by-id")
    @Operation(tags = "Update", summary = "Update Loan By Id")
    public ApiResponse<?> updateById(@RequestBody @Valid LoanRequest loanRequest, @PathVariable("id") Long id) {
        return this.loanService.updateById(id, loanRequest);

    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}/delete-by-id")
    @Operation(tags = "delete", summary = "Delete loan By Id")
    public ApiResponse<?> deleteById(@PathVariable("id") Long id) {
        return this.loanService.deleteById(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/list")
    @Operation(tags = "Get All", summary = "Get All loan list")
    public ApiResponse<?> findAll() {
        return this.loanService.findAll();
    }
}
