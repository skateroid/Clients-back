package com.example.clientsDB.repositories;

import com.example.clientsDB.entity.ClientEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @EntityGraph("ClientFull")
    List<ClientEntity> findClientByFullNameContainingIgnoreCaseOrReversedFullNameContainingIgnoreCase(String fullName, String reversedFullName);

    @EntityGraph("ClientFull")
    List<ClientEntity> findAll();

    @EntityGraph("ClientFull")
    Optional<ClientEntity> findById(Long id);

    @EntityGraph("ClientFull")
    List<ClientEntity> findAllByOrderByFullNameAsc();
}
