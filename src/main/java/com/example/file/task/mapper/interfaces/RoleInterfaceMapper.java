package com.example.file.task.mapper.interfaces;

import com.example.file.task.dto.RoleDto;
import com.example.file.task.entity.UserRole;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleInterfaceMapper {


    UserRole toEntity(RoleDto dto);

    RoleDto toDto(UserRole entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRole(RoleDto dto, @MappingTarget UserRole entity);
}
