package com.example.clientsDB.repositories;

import com.example.clientsDB.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    Optional<Mark> findMarkByNameContainingIgnoreCase(String name);

    Optional<Mark> findMarkById(Long id);
}
