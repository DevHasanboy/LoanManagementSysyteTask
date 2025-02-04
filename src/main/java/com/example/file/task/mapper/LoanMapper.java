package com.example.file.task.mapper;

import com.example.file.task.entity.Loan;
import com.example.file.task.entity.User;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.repository.ClientRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.request.LoanRequest;
import com.example.file.task.response.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;

    public Loan toEntity(LoanRequest request) {
        Loan loan = new Loan();
        loan.setLoanName(request.getLoanName());
        loan.setLoanAmount(request.getLoanAmount());
        User client = this.userRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        loan.setClient(client);
        return loan;
    }

    public LoanResponse toResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setLoanName(loan.getLoanName());
        response.setLoanAmount(loan.getLoanAmount());
        Optional<User> byId = this.userRepository.findById(loan.getClient() != null ? loan.getClient().getId() : null);
        response.setUserId(byId.get().getId());
        return response;
    }

    public void updateToEntityFromRequest(LoanRequest request, Loan loan) {
        if (request == null) return;
        if (request.getLoanAmount() != null) loan.setLoanAmount(request.getLoanAmount());
        if (request.getLoanName() != null) loan.setLoanName(request.getLoanName());
        if (request.getClientId() != null) {
            Optional<User> byId = this.userRepository.findById(request.getClientId());
            byId.ifPresent(loan::setClient);
        }

    }
}
