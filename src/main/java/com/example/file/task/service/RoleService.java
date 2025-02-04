package com.example.file.task.service;

import com.example.file.task.dto.RoleDto;
import com.example.file.task.response.ApiResponse;

import java.util.List;

public interface RoleService {
    ApiResponse<?> create(RoleDto dto);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> updateById(RoleDto dto, Long id);

    ApiResponse<?> deleteById(Long id);

    ApiResponse<List<RoleDto>> findAll();
}
