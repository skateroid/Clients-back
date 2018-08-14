package com.example.clientsDB.mapper;

import com.example.clientsDB.dto.MarkChangeRequest;
import com.example.clientsDB.model.Mark;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarkMapper {
    MarkChangeRequest mapEntity(Mark mark);

    List<MarkChangeRequest> mapEntitiesToModel(List<Mark> markEntity);

    Mark dtoToEntity(MarkChangeRequest request);
}
