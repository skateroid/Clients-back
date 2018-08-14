package com.example.clientsDB.mapper;

import com.example.clientsDB.dto.CityChangeRequest;
import com.example.clientsDB.model.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    List<CityChangeRequest> mapEntitiesToModel(List<City> cities);

    CityChangeRequest mapEntityToModel(City city);

    City mapToEntity(CityChangeRequest request);
}
