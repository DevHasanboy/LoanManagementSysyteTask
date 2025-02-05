package com.example.file.task.controller;

import com.example.file.task.request.TransactionRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.TransactionService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@OpenAPIDefinition(tags = @Tag(name = " Transaction ni boshqarish", description = "Transaction larni boshqarish Api si"))
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(
            summary = "Yangi Transaction yaratish"
    )
    @PostMapping("/create")
    public ApiResponse<?> create(@RequestBody @Valid TransactionRequest request) {
        return this.transactionService.create(request);
    }

    @Operation(
            summary = "Transaction ni ID si orqali olish"
    )
    @GetMapping("/{id}/get_by_id")
    public ApiResponse<?> getById(@PathVariable("id") Long id) {
        return this.transactionService.getById(id);
    }

    @Operation(
            summary = "Transaction ni ID si orqali yangilash"
    )
    @PutMapping("/{id}/update_by_id")
    public ApiResponse<?> updateById(@RequestBody @Valid TransactionRequest request, @PathVariable("id") Long id) {
        return this.transactionService.updateById(request, id);
    }

    @Operation(
            summary = "Transaction ni ID si orqali o`chirish"
    )
    @DeleteMapping("/{id}/delete_by_id")
    public ApiResponse<?> deleteById(@PathVariable("id") Long id) {
        return this.transactionService.deleteById(id);
    }

    @Operation(
            summary = "Transaction ni ID si orqali o`chirish"
    )
    @DeleteMapping("/list")
    public ApiResponse<?> findAll() {
        return this.transactionService.findAll();
    }

}
