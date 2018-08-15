package com.example.clientsDB.service;

import com.example.clientsDB.dto.*;
import com.example.clientsDB.entity.CarEntity;
import com.example.clientsDB.entity.ClientEntity;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.mapper.ClientMapper;
import com.example.clientsDB.model.Client;
import com.example.clientsDB.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    @Autowired
    private ClientService clientService;

//    @Value("${application.clientId}")
//    private String property;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<Client> getAll() {
        if (clientRepository.findAll().isEmpty()) {
            return new ArrayList<>();
        }
        return clientMapper.mapEntityListToModel(clientRepository.findAll());
    }

    public Client getClientById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return clientMapper.mapEntityToModel(clientEntity);
    }

    public List<Client> getClientByFullName(String fullName) {
        fullName = fullName.replaceAll(" ", "% ");
        List<ClientEntity> clientEntityList = clientRepository.findClientByFullNameContainingIgnoreCaseOrReversedFullNameContainingIgnoreCase(fullName, fullName);
        return clientMapper.mapEntityListToModel(clientEntityList);
    }

    public Client createClient(ClientChangeRequest request) {
        ClientEntity clientEntity = clientMapper.mapToEntity(request);
        for (CarEntity car : clientEntity.getCars()) {
            car.setClient(clientEntity);
        }
        clientRepository.save(clientEntity);

        return clientService.getClientById(clientEntity.getId());
    }

    public Client updateClient(Long id, ClientChangeRequest request) {
        ClientEntity clientEntityOld = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        ClientEntity clientEntityNew = clientMapper.mapToEntity(request);

        HashSet<Long> clientEntityOldSetId = (HashSet<Long>) clientEntityOld.getCars().stream()
                .map(CarEntity::getId).collect(Collectors.toSet());
        HashSet<Long> clientEntityNewSetId = (HashSet<Long>) clientEntityNew.getCars().stream()
                .map(CarEntity::getId).collect(Collectors.toSet());
        HashSet<Long> diff = (HashSet<Long>) clientEntityNewSetId.stream().filter(o -> !clientEntityOldSetId.contains(o)).collect(Collectors.toSet());
        if (!diff.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            diff.forEach(n -> builder.append(n).append(", "));
            throw new EntityNotFoundException(String.format("Car with id: %s not found", builder.toString().substring(0, builder.length() - 2)));
        }
        for (CarEntity carEntityNew : clientEntityNew.getCars()) {
            carEntityNew.setClient(clientEntityNew);
        }
        clientEntityNew.setId(clientEntityOld.getId());
        clientRepository.save(clientEntityNew);

        return clientService.getClientById(id);
    }

    public void deleteClient(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Client not found");
        }
    }
}
