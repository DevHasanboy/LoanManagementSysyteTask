package com.example.file.task.service.impl;

import com.example.file.task.dto.RoleDto;
import com.example.file.task.entity.AuditLogs;
import com.example.file.task.entity.UserRole;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.mapper.RoleMapper;
import com.example.file.task.repository.RoleRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.RoleService;
import com.example.file.task.utils.Util;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;
    private final Util util;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public ApiResponse<?> create(RoleDto dto) {
        UserRole entity = this.roleMapper.toEntity(dto);
        roleRepository.save(entity);
        logger.info("Role created successfully");
        return ApiResponse.builder()
                .success(true)
                .message("Role created successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    @Override
    public ApiResponse<?> getById(Long id) {
        UserRole role = this.roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        RoleDto dto = this.roleMapper.toDto(role);
        return ApiResponse.builder()
                .success(true)
                .message("Role by id successfully")
                .data(dto)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    @Override
    public ApiResponse<?> updateById(RoleDto dto, Long id) {
        UserRole role = this.roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        this.roleMapper.updateToRoleFromDto(role, dto);
        this.roleRepository.save(role);
        return ApiResponse.builder()
                .success(true)
                .message("Role updated successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        UserRole role = this.roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        this.roleRepository.delete(role);
        return ApiResponse.builder()
                .success(true)
                .message("Role deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();

    }

    @Override
    public ApiResponse<List<RoleDto>> findAll() {
        List<UserRole> all = this.roleRepository.findAll();
        List<RoleDto> list = new ArrayList<>();

        if (!all.isEmpty()) {
            for (UserRole role : all) {
                RoleDto dto = this.roleMapper.toDto(role);
                list.add(dto);
            }
            return ApiResponse.<List<RoleDto>>builder()
                    .success(true)
                    .message("Get all roles successfully")
                    .data(list)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return ApiResponse.<List<RoleDto>>builder()
                .success(true)
                .message("Get all roles successfully")
                .data(new ArrayList<>())
                .httpStatus(HttpStatus.OK)
                .build();
    }

}
