package com.example.file.task.controller;

import com.example.file.task.dto.RoleDto;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")

@Tag(name = "Role", description = "Role APIs")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            tags = "Create",
            summary = "Create new role")
    @PostMapping("/crete")
    public ApiResponse<?> create(@RequestBody RoleDto dto) {
        return this.roleService.create(dto);

    }

    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            tags = "Get",
            summary = "Get role by id"
    )
    @GetMapping("/{id}/get-by-id")
    public ApiResponse<?> getById(@PathVariable("id") Long id) {
        return this.roleService.getById(id);

    }

    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            tags = "Update",
            summary = "Update role by id"
    )
    @PutMapping("/{id}/update-by-id")
    public ApiResponse<?> updateById(@PathVariable("id") Long id, @RequestBody RoleDto dto) {
        return this.roleService.updateById(dto, id);

    }

    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            tags = "Delete",
            summary = "Delete role by id"
    )
    @DeleteMapping("/{id}/delete-by-id")
    public ApiResponse<?> deleteById(@PathVariable("id") Long id) {
        return this.roleService.deleteById(id);

    }

    @PreAuthorize("hasRole('MANAGER')")
    @Operation(
            tags = "Get List",
            summary = "Role larni List ini olish"
    )
    @GetMapping("/list")
    public ApiResponse<List<RoleDto>> findAll() {
        return this.roleService.findAll();
    }
}
