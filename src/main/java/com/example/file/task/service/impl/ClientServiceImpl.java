package com.example.file.task.service.impl;

import com.example.file.task.entity.User;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.mapper.UserMapper;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.UserRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.response.UserResponse;
import com.example.file.task.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public ApiResponse<?> getById(Long id) {
        User client = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        UserResponse response = this.userMapper.toResponse(client);
        return ApiResponse.builder()
                .success(true)
                .message("client by ID successfully")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> updateById(UserRequest request, Long id) {
        User client = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        this.userMapper.updateToEntityFromResponse(client, request);
        this.userRepository.save(client);
        return ApiResponse.builder()
                .success(true)
                .message("Client Update Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        User client = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        this.userRepository.delete(client);
        return ApiResponse.builder()
                .success(true)
                .message("Client Delete Successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> findAll() {
        List<User> list = this.userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        if (!list.isEmpty()) {
            for (User user : list) {
                result.add(userMapper.toResponse(user));
            }
            return ApiResponse.builder().message("User List").success(true).data(result).httpStatus(HttpStatus.OK).build();
        }
        return ApiResponse.builder().message("User List").success(true).data(new ArrayList<>()).httpStatus(HttpStatus.OK).build();
    }
}
