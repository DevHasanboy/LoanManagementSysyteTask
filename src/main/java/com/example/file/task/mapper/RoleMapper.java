package com.example.file.task.mapper;

import com.example.file.task.dto.RoleDto;
import com.example.file.task.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public UserRole toEntity(RoleDto dto) {
        UserRole role = new UserRole();
        role.setName(dto.getName());
        return role;
    }

    public RoleDto toDto(UserRole role) {
        RoleDto dto = new RoleDto();
        dto.setName(role.getName());
        return dto;
    }

    public void updateToRoleFromDto(UserRole role, RoleDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null) {
            role.setName(dto.getName());
        }
    }
}
