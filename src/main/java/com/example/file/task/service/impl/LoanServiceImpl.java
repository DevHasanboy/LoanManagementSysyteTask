package com.example.file.task.service.impl;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.entity.Loan;
import com.example.file.task.exception.ResourceNotFoundException;
import com.example.file.task.mapper.LoanMapper;
import com.example.file.task.repository.LoanRepository;
import com.example.file.task.request.LoanRequest;
import com.example.file.task.response.ApiResponse;
import com.example.file.task.response.LoanResponse;
import com.example.file.task.service.LoanService;
import com.example.file.task.validation.LoanValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanValidation loanValidation;

    @Override
    public ApiResponse<?> create(LoanRequest request) {
        List<ErrorDto> errorList = this.loanValidation.validate(request);
        if (!errorList.isEmpty()) {
            return ApiResponse.builder()
                    .message("Validation Failed")
                    .errorsList(errorList)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Loan entity = this.loanMapper.toEntity(request);
        loanRepository.save(entity);
        return ApiResponse.builder()
                .success(true)
                .message("Loan create successfully")
                .data(entity)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Loan loan = this.loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        LoanResponse response = this.loanMapper.toResponse(loan);
        return ApiResponse.builder()
                .success(true)
                .message("Loan get successfully")
                .data(response)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> updateById(Long id, LoanRequest request) {
        Loan loan = this.loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        this.loanMapper.updateToEntityFromRequest(request, loan);
        this.loanRepository.save(loan);
        return ApiResponse.builder()
                .success(true)
                .message("Loan update successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> deleteById(Long id) {
        Loan loan = this.loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        this.loanRepository.delete(loan);
        return ApiResponse.builder()
                .success(true)
                .message("Loan delete successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> findAll() {
        List<Loan> list = this.loanRepository.findAll();
        List<LoanResponse> result = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Loan loan : list) {
                LoanResponse response = this.loanMapper.toResponse(loan);
                result.add(response);
            }
            return ApiResponse.builder().success(true).message("Loan List").data(result).httpStatus(HttpStatus.OK).build();
        }
        return ApiResponse.builder().success(true).message("Loan List").data(new ArrayList<>()).httpStatus(HttpStatus.OK).build();
    }
}
