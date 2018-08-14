package com.example.clientsDB.repositories;

import com.example.clientsDB.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findCityByNameContainingIgnoreCase(String name);

    Optional<CityEntity> findCityById(Long id);
}
