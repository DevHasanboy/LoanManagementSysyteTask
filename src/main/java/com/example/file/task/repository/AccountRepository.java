package com.example.file.task.repository;

import com.example.file.task.entity.Accounts;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {

    @EntityGraph(
            attributePaths = {
                    "client"
            }
    )
    Optional<Accounts> findById(Long id);
}
