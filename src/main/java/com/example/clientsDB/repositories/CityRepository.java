package com.example.clientsDB.repositories;

import com.example.clientsDB.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findCityById(Long id);

    List<CityEntity> findCitiesByNameContainingIgnoreCase(String name);

    List<CityEntity> findAllByOrderByNameAsc();
}
