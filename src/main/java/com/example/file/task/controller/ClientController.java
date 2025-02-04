package com.example.file.task.controller;

import com.example.file.task.request.ClientRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@Tag(name = "Client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    @Operation(
            tags = "Create",
            summary = "Create new Client"
    )
    public ApiResponse<?> create(@RequestBody @Valid ClientRequest clientRequest) {
        return this.clientService.create(clientRequest);

    }

    @GetMapping("/{id}/get-by-id")
    @Operation(
            tags = "Get",
            summary = "Get Client By Id"
    )
    public ApiResponse<?> getById(@PathVariable("id") Long id) {
        return this.clientService.getById(id);

    }

    @PutMapping("/{id}/update-by-id")
    @Operation(
            tags = "Update",
            summary = "Update Client By Id"
    )
    public ApiResponse<?> updateById(@PathVariable("id") Long id, @RequestBody @Valid ClientRequest clientRequest) {
        return this.clientService.updateById(clientRequest, id);

    }

    @DeleteMapping("/{id}/delete-by-id")
    @Operation(
            tags = "Delete",
            summary = "Delete Client By Id"
    )
    public ApiResponse<?> deleteById(@PathVariable("id") Long id) {
        return this.clientService.deleteById(id);

    }
}
