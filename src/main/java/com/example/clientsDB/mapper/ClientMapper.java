package com.example.clientsDB.mapper;

import com.example.clientsDB.dto.ClientChangeRequest;
import com.example.clientsDB.entity.ClientEntity;
import com.example.clientsDB.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientEntity mapToEntity(ClientChangeRequest request);

    List<Client> mapEntityListToModel(List<ClientEntity> clientEntity);

    Client mapEntityToModel(ClientEntity clientEntity);
}
