package com.example.file.task.mapper;

import com.example.file.task.entity.Accounts;
import com.example.file.task.entity.Client;
import com.example.file.task.entity.User;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.repository.ClientRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.AccountRequest;
import com.example.file.task.request.ClientRequest;
import com.example.file.task.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;

    public Accounts toEntity(AccountRequest request) {
        Accounts accounts = new Accounts();
        accounts.setAccountType(request.getAccountType());
        accounts.setBalance(request.getBalance());
        accounts.setInterestRate(request.getInterestRate());
        User client = this.userRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        accounts.setClient(client);
        return accounts;
    }

    public AccountResponse toResponse(Accounts accounts) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountType(accounts.getAccountType());
        accountResponse.setBalance(accounts.getBalance());
        accountResponse.setInterestRate(accounts.getInterestRate());
        if (accounts.getClient() != null) {
            User client = this.userRepository.findById(accounts.getClient().getId()).orElseThrow(()
                    -> new ResourceNotFoundException("Client not found"));
            accountResponse.setClientId(client.getId());
        }

        return accountResponse;
    }

    public void updateToEntityFromRequest(AccountRequest request, Accounts accounts) {
        if (request == null) return;
        if (request.getAccountType() != null) {
            accounts.setAccountType(request.getAccountType());
        }
        if (request.getBalance() != null) {
            accounts.setBalance(request.getBalance());
        }
        if (request.getInterestRate() != null) {
            accounts.setInterestRate(request.getInterestRate());
        }
        if (request.getClientId() != null) {
            Optional<User> byId = this.userRepository.findById(request.getClientId());
            byId.ifPresent(accounts::setClient);
        }
    }
}
