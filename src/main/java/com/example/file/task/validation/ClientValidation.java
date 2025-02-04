package com.example.file.task.validation;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.repository.ClientRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientValidation {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public List<ErrorDto> validate(ClientRequest request) {
        List<ErrorDto> errors = new ArrayList<>();
        if (this.clientRepository.existsByEmail(request.getEmail())) {
            errors.add(new ErrorDto("email", "Email already exists"));
        }
        if (request.getUserId() == null || this.userRepository.findById(request.getUserId()).isEmpty()) {
            errors.add(new ErrorDto("userId", "User not found"));
        }
        return errors;

    }
}
