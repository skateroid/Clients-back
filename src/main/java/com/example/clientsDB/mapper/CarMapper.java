package com.example.clientsDB.mapper;

import com.example.clientsDB.entity.CarEntity;
import com.example.clientsDB.model.Car;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CarMapper {

    List<Car> mapEntitiesToModel(Set<CarEntity> carEntities);
}
