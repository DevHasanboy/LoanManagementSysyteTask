package com.example.file.task.mapper.interfaces;

import com.example.file.task.entity.Accounts;
import com.example.file.task.request.AccountRequest;
import com.example.file.task.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {UserInterfaceMapper.class})
public interface AccountInterfaceMapper {

    @Mapping(source = "clientId",target = "client.id")
    Accounts toEntity(AccountRequest request);

    @Mapping(source = "client.id",target = "clientId")
    AccountResponse toResponse(Accounts accounts);

    @Mapping(source = "clientId",target = "client.id")
    void updateToEntityFromRequest(AccountRequest request, @MappingTarget Accounts accounts);
}
