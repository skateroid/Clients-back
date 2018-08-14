package com.example.clientsDB.mapper;

import com.example.clientsDB.dto.CityChangeRequest;
import com.example.clientsDB.entity.CityEntity;
import com.example.clientsDB.model.City;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    List<City> mapEntitiesToModel(List<CityEntity> cities);

    City mapEntityToModel(CityEntity cityEntity);

    CityEntity mapToEntity(CityChangeRequest request);
}
