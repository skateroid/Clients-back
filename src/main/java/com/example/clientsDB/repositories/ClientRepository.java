package com.example.clientsDB.repositories;

import com.example.clientsDB.model.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @EntityGraph("ClientFull")
    List<Client> findClientByFullNameContainingIgnoreCaseOrReversedFullNameContainingIgnoreCase(String fullName, String reversedFullName);

    @EntityGraph("ClientFull")
    List<Client> findAll();

    @EntityGraph("ClientFull")
    Optional<Client> findById(Long id);
}
