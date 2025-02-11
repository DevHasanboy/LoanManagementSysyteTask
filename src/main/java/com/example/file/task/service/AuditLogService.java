package com.example.file.task.service;

import com.example.file.task.entity.AuditLogs;
import com.example.file.task.entity.User;
import com.example.file.task.repository.AuditLogRepository;
import com.example.file.task.repository.UserRepository;
import com.example.file.task.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository; // Foydalanuvchini olish uchun
    private final Util util;

    @Transactional
    public void saveLog(String action, String entityName, String methodName, String parameters, String status, String errorMessage) {
        AuditLogs log = new AuditLogs();
        log.setAction(action);
        log.setEntityName(entityName);
        log.setMethodName(methodName);
        log.setParameters(parameters);
        log.setStatus(status);
        log.setErrorMessage(errorMessage);
        log.setCreatedAt(LocalDateTime.now());

        // Hozirgi foydalanuvchini olish
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findByUsername(username).ifPresent(log::setManager);
        User currentManager = util.getCurrentManager();
        log.setManager(currentManager);
        auditLogRepository.save(log);
    }
}

