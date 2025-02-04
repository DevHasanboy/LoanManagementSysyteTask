package com.example.file.task.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogRequest {
    private Long managerId;
    private String action;
}
