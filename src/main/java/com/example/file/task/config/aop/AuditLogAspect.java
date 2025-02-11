package com.example.file.task.config.aop;

import com.example.file.task.entity.AuditLogs;
import com.example.file.task.entity.User;
import com.example.file.task.repository.AuditLogRepository;
import com.example.file.task.repository.UserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        // Metod va parametrlar haqida ma'lumotlar
        String methodName = joinPoint.getSignature().toShortString();
        String entityName = joinPoint.getTarget().getClass().getSimpleName();
        String parameters = Arrays.toString(joinPoint.getArgs());
        String action = getActionFromMethod(methodName);
        String status = "";
        String errorMessage = null;

        // Tokenni olish (HTTP so'rov kontekstida)
        String token = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                token = request.getHeader("Authorization");
            }
        } catch (Exception e) {
            token = "N/A";
        }

        Object result;
        try {
            result = joinPoint.proceed();
            status = "SUCCESS";
        } catch (Exception e) {
            status = "FAILED";
            errorMessage = e.getMessage();
            throw e;
        } finally {
            // AuditLogs entitiga barcha ma'lumotlarni to'ldirib, saqlash:
            AuditLogs log = new AuditLogs();
            log.setAction(action);
            log.setEntityName(entityName);
            log.setMethodName(methodName);
            log.setParameters(parameters);
            log.setStatus(status);
            log.setErrorMessage(errorMessage);
            log.setToken(token);

            // Hozirgi foydalanuvchini olish:
            String username = "Anonymous";
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    username = authentication.getName();
                }
            } catch (Exception ex) {
                // Agarda olishda xatolik bo'lsa, "Anonymous" qoladi
            }
            Optional<User> optionalUser = userRepository.findByUsername(username);
            optionalUser.ifPresent(log::setManager);

            auditLogRepository.save(log);
        }
        return result;
    }

    private String getActionFromMethod(String methodName) {
        if (methodName.contains("save") || methodName.contains("create")) return "CREATE";
        if (methodName.contains("update")) return "UPDATE";
        if (methodName.contains("delete") || methodName.contains("remove")) return "DELETE";
        if (methodName.contains("get") || methodName.contains("find")) return "READ";
        return "UNKNOWN";
    }
}


