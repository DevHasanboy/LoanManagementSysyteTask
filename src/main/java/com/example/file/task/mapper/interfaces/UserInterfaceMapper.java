package com.example.file.task.mapper.interfaces;

import com.example.file.task.entity.User;
import com.example.file.task.entity.UserRole;
import com.example.file.task.repository.RoleRepository;
import com.example.file.task.request.UserRequest;
import com.example.file.task.response.UserResponse;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {RoleInterfaceMapper.class})
public interface UserInterfaceMapper {

    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequest userRequest, @Context RoleRepository roleRepository);

    default User mapToEntity(UserRequest userRequest, @Context RoleRepository attachmentRepository) {
        User user = toEntity(userRequest, attachmentRepository);

        // `RoleIds` bo'yicha `Role` obyektlarini yuklash
        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            Set<UserRole> roles = new HashSet<>(attachmentRepository.findAllById(userRequest.getRoles()));
            user.setRole(roles);
        }

        return user;
    }

    @Mapping(source = "role", target = "roles")
    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateToEntityFromRequest(@MappingTarget User user, UserRequest userRequest);
}
