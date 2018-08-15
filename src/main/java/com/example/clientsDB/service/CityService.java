package com.example.clientsDB.service;

import com.example.clientsDB.dto.CityChangeRequest;
import com.example.clientsDB.entity.CityEntity;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.mapper.CityMapper;
import com.example.clientsDB.model.City;
import com.example.clientsDB.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;
    private CityMapper cityMapper;
    @Autowired
    private CityService cityService;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public City findCityById(Long id) {
        CityEntity cityEntity = cityRepository.findCityById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("CityEntity with id: %d not found", id)));
        return  cityMapper.mapEntityToModel(cityEntity);
//        return cityRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("CityEntity with id: %d not found", id)));
    }

    public List<City> findCityByName(String name) {
        name = name.replaceAll(" ", "%");
        List<CityEntity> cityEntityList = cityRepository.findCitiesByNameContainingIgnoreCase(name);
        return cityMapper.mapEntitiesToModel(cityEntityList);
//        CityEntity cityEntity = cityRepository.findCityByNameContainingIgnoreCase(name)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("CityEntity: %s not found", name)));
//        return cityMapper.mapEntityToModel(cityEntity);
//        return cityRepository.findCityByNameContainingIgnoreCase(name)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("CityEntity: %s not found", name)));
    }

    public List<City> getAll() {
        if (cityRepository.findAll().isEmpty()) {
            return new ArrayList<>();
        }
        return cityMapper.mapEntitiesToModel(cityRepository.findAll());
//        return cityRepository.findAll();
    }

    public City createCity(CityChangeRequest cityChangeRequest) {
//        CityEntity newCity = new CityEntity();
//        newCity.setName(cityChangeRequest.getName());
        CityEntity cityEntity = cityMapper.mapToEntity(cityChangeRequest);
        cityRepository.save(cityEntity);

        return cityService.findCityById(cityEntity.getId());
    }

    public City updateCity(Long id, CityChangeRequest request) {
//        CityEntity currentCity = findCityById(id);
//        currentCity.setName(request.getName());
        CityEntity cityEntityOld = cityRepository.findCityById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("CityEntity with id: %d not found", id)));
        CityEntity cityEntityNew = cityMapper.mapToEntity(request);
        cityEntityNew.setId(cityEntityOld.getId());
        cityRepository.save(cityEntityNew);

        return cityService.findCityById(id);
    }

    public void deleteCity(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("CityEntity with id: %d not found", id));
        }
    }
}
