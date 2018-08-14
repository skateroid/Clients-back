package com.example.clientsDB.repositories;

import com.example.clientsDB.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkRepository extends JpaRepository<MarkEntity, Long> {

    Optional<MarkEntity> findMarkByNameContainingIgnoreCase(String name);

    Optional<MarkEntity> findMarkById(Long id);
}
