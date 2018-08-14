package com.example.clientsDB.service;

import com.example.clientsDB.dto.*;
import com.example.clientsDB.entity.CarEntity;
import com.example.clientsDB.entity.ClientEntity;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.mapper.ClientMapper;
import com.example.clientsDB.model.Client;
import com.example.clientsDB.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return clientMapper.mapEntityListToModel(clientRepository.findAll());
//        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClientEntity not found"));
        return clientMapper.mapEntityToModel(clientEntity);
//        return clientRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("ClientEntity not found"));
    }

    public List<Client> getClientByFullName(String fullName) {
        fullName = fullName.replaceAll(" ", "% ");
        List<ClientEntity> clientEntityList = clientRepository.findClientByFullNameContainingIgnoreCaseOrReversedFullNameContainingIgnoreCase(fullName, fullName);
        return clientMapper.mapEntityListToModel(clientEntityList);
    }

    public Client createClient(ClientChangeRequest request) {
        ClientEntity clientEntity = clientMapper.mapToEntity(request);
        clientRepository.save(clientEntity);

        return clientService.getClientById(clientEntity.getId());
//        ClientEntity newClient = new ClientEntity();
//        newClient.setName(request.getName());
//        newClient.setLastname(request.getLastname());
//        newClient.setPatronymic(request.getPatronymic());
//        // set carEntities set to clientEntity
//        newClient.setCarEntities(internalChangeClientCar(request, newClient));
//
//        // collect all cities to set
//        Set<CityEntity> citySet = new HashSet<>();
//        for (LinkToCity city : request.getCity()) {
//            CityEntity newCity = new CityEntity();
//            newCity.setId(city.getId());
//            // set clientEntity to city
//            citySet.add(newCity);
//        }
//        // set cities set to clientEntity
//        newClient.setCities(citySet);
//
//        clientRepository.save(newClient);
    }

    public Client updateClient(Long id, ClientChangeRequest request) {
        ClientEntity clientEntityOld = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ClientEntity not found"));
        ClientEntity clientEntityNew = clientMapper.mapToEntity(request);
        for (CarEntity carEntity : clientEntityNew.getCars()) {
            carEntity.setClient(clientEntityNew);
        }
        clientEntityNew.setId(clientEntityOld.getId());
        clientRepository.save(clientEntityNew);

        return clientService.getClientById(id);
//        ClientEntity currentClient = getClientById(id);
//        currentClient.getCarEntities().clear();
//
//        currentClient.setName(request.getName());
//        currentClient.setLastname(request.getLastname());
//        currentClient.setPatronymic(request.getPatronymic());
//
//        Set<CityEntity> citySet = new HashSet<>();
//        for (LinkToCity city : request.getCity()) {
//            CityEntity newCity = new CityEntity();
//            newCity.setId(city.getId());
//            citySet.add(newCity);
//        }
//        currentClient.setCities(citySet);
//
//        currentClient.getCarEntities().addAll(internalChangeClientCar(request, currentClient));
//        clientRepository.save(currentClient);
    }

//    private Set<CarEntity> internalChangeClientCar(ClientChangeRequest request, ClientEntity newClient) {
//        Set<CarEntity> carSet = new HashSet<>();
//        for (CarChangeRequest car : request.getCar()) {
//            CarEntity newCar = new CarEntity();
//            newCar.setId(car.getId());
//            newCar.setClientEntity(newClient);
//            newCar.setName(car.getName());
//            newCar.setRegistrationPlate(car.getRegistrationPlate());
//
//            MarkEntity markEntity = new MarkEntity();
//            markEntity.setId(car.getMarkEntity().getId());
//            newCar.setMarkEntity(markEntity);
//
//            carSet.add(newCar);
//
//        }
//        return carSet;
//    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
