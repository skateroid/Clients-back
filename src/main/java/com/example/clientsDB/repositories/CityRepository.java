package com.example.clientsDB.repositories;

import com.example.clientsDB.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findCityByNameContainingIgnoreCase(String name);

    Optional<City> findCityById(Long id);
}
