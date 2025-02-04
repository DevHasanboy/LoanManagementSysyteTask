package com.example.file.task.validation;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.repository.ClientRepository;
import com.example.file.task.request.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountValidation {
    private final ClientRepository clientRepository;

    public List<ErrorDto> validate(AccountRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (request.getClientId() == null || this.clientRepository.findById(request.getClientId()).isEmpty()) {
            errors.add(new ErrorDto("clientId", "Client not found"));
        }
        return errors;
    }

}
