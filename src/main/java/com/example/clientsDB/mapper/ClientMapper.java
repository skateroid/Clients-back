package com.example.clientsDB.mapper;

import com.example.clientsDB.dto.ClientChangeRequest;
import com.example.clientsDB.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client mapToEntity(ClientChangeRequest request);

    List<ClientChangeRequest> mapEntityListToModel(List<Client> client);

    ClientChangeRequest mapEntityToModel(Client client);
}
