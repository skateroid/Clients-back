package com.example.clientsDB.service;

import com.example.clientsDB.dto.CityChangeRequest;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.mapper.CityMapper;
import com.example.clientsDB.model.City;
import com.example.clientsDB.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public CityChangeRequest findCityById(Long id) {
        City city = cityRepository.findCityById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("City with id: %d not found", id)));
        return  cityMapper.mapEntityToModel(city);
//        return cityRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("City with id: %d not found", id)));
    }

    public CityChangeRequest findCityByName(String name) {
        City city = cityRepository.findCityByNameContainingIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("City: %s not found", name)));
        return cityMapper.mapEntityToModel(city);
//        return cityRepository.findCityByNameContainingIgnoreCase(name)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("City: %s not found", name)));
    }

    public List<CityChangeRequest> getAll() {
        return cityMapper.mapEntitiesToModel(cityRepository.findAll());
//        return cityRepository.findAll();
    }

    public CityChangeRequest createCity(CityChangeRequest cityChangeRequest) {
//        City newCity = new City();
//        newCity.setName(cityChangeRequest.getName());
        City city = cityMapper.mapToEntity(cityChangeRequest);
        cityRepository.save(city);

        return cityService.findCityById(city.getId());
    }

    public CityChangeRequest updateCity(Long id, CityChangeRequest request) {
//        City currentCity = findCityById(id);
//        currentCity.setName(request.getName());
        City cityOld = cityRepository.findCityById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("City with id: %d not found", id)));
        City cityNew = cityMapper.mapToEntity(request);
        cityNew.setId(cityOld.getId());
        cityRepository.save(cityNew);

        return cityService.findCityById(id);
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
