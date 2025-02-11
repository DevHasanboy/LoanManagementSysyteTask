package com.example.file.task.mapper.interfaces;

import com.example.file.task.entity.Loan;
import com.example.file.task.request.LoanRequest;
import com.example.file.task.response.LoanResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserInterfaceMapper.class})
public interface LoanInterfaceMapper {

    @Mapping(source = "clientId", target = "client.id")
    Loan toEntity(LoanRequest loanRequest);

    @Mapping(source = "client.id", target = "userId")
    LoanResponse toResponse(Loan loan);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "clientId", target = "client.id")
    void updateToEntityFromRequest(LoanRequest loanRequest, @MappingTarget Loan loan);
}
