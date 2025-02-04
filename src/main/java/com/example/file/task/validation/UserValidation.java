package com.example.file.task.validation;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;

    public List<ErrorDto> validate(UserRequest request) {
        List<ErrorDto> errors = new ArrayList<>();

        if (this.userRepository.existsByUsername(request.getUsername())) {
            errors.add(new ErrorDto("username", "Username already exists"));
        }
        return errors;
    }
}
