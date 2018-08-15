package com.example.clientsDB.repositories;

import com.example.clientsDB.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<MarkEntity, Long> {

    Optional<MarkEntity> findMarkById(Long id);

    List<MarkEntity> findMarkByNameContainingIgnoreCase(String name);
}
