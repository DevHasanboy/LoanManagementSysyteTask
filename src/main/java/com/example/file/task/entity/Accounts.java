package com.example.file.task.entity;

import com.example.file.task.enums.AccountType;
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
@Table(name = "account_tables")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private User client;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "interest_rate")
    private Double interestRate;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
