package com.example.file.task.mapper;

import com.example.file.task.entity.Client;
import com.example.file.task.entity.User;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientMapper {
    private final UserRepository userRepository;

    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        client.setUser(user);
        return client;
    }

    public ClientRequest toResponse(Client client) {
        ClientRequest response = new ClientRequest();
        response.setEmail(client.getEmail());
        response.setName(client.getName());
        response.setPhone(client.getPhone());
        Optional<User> byId = this.userRepository.findById(client.getUser() != null ? client.getUser().getId() : null);
        byId.ifPresent(user -> response.setUserId(user.getId()));
        return response;
    }

    public void updateToEntityFromResponse(ClientRequest request, Client client) {
        if (request == null) {
            return;
        }
        if (request.getName() != null) {
            client.setName(request.getName());
        }
        if (request.getPhone() != null) {
            client.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            client.setEmail(request.getEmail());
        }

        Optional<User> byId = this.userRepository.findById(request.getUserId());
        byId.ifPresent(client::setUser);
    }

}
