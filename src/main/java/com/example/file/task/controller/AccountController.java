package com.example.file.task.controller;

import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account APIs")
public class AccountController {
    private final AccountsService accountsService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create")
    @Operation(tags = "Create",
            summary = "Create new account"
    )
    public ApiResponse<?> create(@RequestBody @Valid AccountRequest accountRequest) {
        return accountsService.create(accountRequest);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}/get-by-id")
    @Operation(
            tags = "Get",
            summary = "Get account by id"
    )
    public ApiResponse<?> getById(@PathVariable("id") Long id) {
        return accountsService.getById(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/update-by-id")
    @Operation(
            tags = "Update",
            summary = "Update account by id"
    )

    public ApiResponse<?> updateById(@PathVariable("id") Long id, @RequestBody @Valid AccountRequest accountRequest) {
        return accountsService.updateById(id, accountRequest);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}/delete-by-id")
    @Operation(
            tags = "Delete",
            summary = "Delete account by id"
    )
    public ApiResponse<?> deleteById(@PathVariable("id") Long id) {
        return accountsService.deleteById(id);
    }

}
