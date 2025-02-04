package com.example.file.task.service.impl;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.entity.Client;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.mapper.ClientMapper;
import com.example.file.task.repository.ClientRepository;
import com.example.file.task.request.ClientRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.service.ClientService;
import com.example.file.task.validation.ClientValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientValidation clientValidation;

    @Override
    public ApiResponse<?> create(ClientRequest request) {
        List<ErrorDto> errorList = this.clientValidation.validate(request);
        if (!errorList.isEmpty()) {
            return ApiResponse.builder()
                    .message("Validation failed")
                    .errorsList(errorList)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        Client entity = this.clientMapper.toEntity(request);
        clientRepository.save(entity);
        return ApiResponse.builder()
                .success(true)
                .message("Client created successfully")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        ClientRequest response = this.clientMapper.toResponse(client);
        return ApiResponse.builder()
                .success(true)
                .message("client by ID successfully")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> updateById(ClientRequest request, Long id) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        this.clientMapper.updateToEntityFromResponse(request, client);
        this.clientRepository.save(client);
        return ApiResponse.builder()
                .success(true)
                .message("Client Update Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        this.clientRepository.delete(client);
        return ApiResponse.builder()
                .success(true)
                .message("Client Delete Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
