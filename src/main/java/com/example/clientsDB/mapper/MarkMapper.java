package com.example.clientsDB.mapper;

import com.example.clientsDB.dto.MarkChangeRequest;
import com.example.clientsDB.entity.MarkEntity;
import com.example.clientsDB.model.Mark;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarkMapper {
    Mark mapEntityToModel(MarkEntity markEntity);

    List<Mark> mapEntitiesToModel(List<MarkEntity> markEntity);

    MarkEntity dtoToEntity(MarkChangeRequest request);
}
