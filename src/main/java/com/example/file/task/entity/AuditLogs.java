package com.example.file.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_logs_table")
public class AuditLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    private String action;        // CRUD amallar (CREATE, UPDATE, DELETE, ...)
    private String entityName;    // Entity nomi (masalan, User, Order, ...)
    private String methodName;    // Qaysi metod ishlatilgani
    private String parameters;    // Kiruvchi parametrlar
    private String status;        // SUCCESS yoki FAILED
    private String errorMessage;// Xato bo'lsa, xato matni
    @Column(columnDefinition = "TEXT")
    private String token;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
