package com.example.clientsDB.service;

import com.example.clientsDB.dto.*;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.mapper.ClientMapper;
import com.example.clientsDB.model.Car;
import com.example.clientsDB.model.Client;
import com.example.clientsDB.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    public List<ClientChangeRequest> getAll() {
        return clientMapper.mapEntityListToModel(clientRepository.findAll());
//        return clientRepository.findAll();
    }

    public ClientChangeRequest getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return clientMapper.mapEntityToModel(client);
//        return clientRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    public List<ClientChangeRequest> getClientByFullName(String fullName) {
        fullName = fullName.replaceAll(" ", "% ");
        List<Client> clientList = clientRepository.findClientByFullNameContainingIgnoreCaseOrReversedFullNameContainingIgnoreCase(fullName, fullName);
//        return clientRepository.findClientByFullNameContainingIgnoreCaseOrReversedFullNameContainingIgnoreCase(fullName, fullName);
        return clientMapper.mapEntityListToModel(clientList);
    }

    public ClientChangeRequest createClient(ClientChangeRequest request) {
        Client client = clientMapper.mapToEntity(request);
        clientRepository.save(client);

        return clientService.getClientById(client.getId());
//        Client newClient = new Client();
//        newClient.setName(request.getName());
//        newClient.setLastname(request.getLastname());
//        newClient.setPatronymic(request.getPatronymic());
//        // set cars set to client
//        newClient.setCars(internalChangeClientCar(request, newClient));
//
//        // collect all cities to set
//        Set<City> citySet = new HashSet<>();
//        for (LinkToCity city : request.getCity()) {
//            City newCity = new City();
//            newCity.setId(city.getId());
//            // set client to city
//            citySet.add(newCity);
//        }
//        // set cities set to client
//        newClient.setCities(citySet);
//
//        clientRepository.save(newClient);
    }

    public ClientChangeRequest updateClient(Long id, ClientChangeRequest request) {
        Client clientOld = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Client clientNew = clientMapper.mapToEntity(request);
        for (Car car : clientNew.getCars()) {
            car.setClient(clientNew);
        }
        clientNew.setId(clientOld.getId());
        clientRepository.save(clientNew);

        return clientService.getClientById(id);
//        Client currentClient = getClientById(id);
//        currentClient.getCars().clear();
//
//        currentClient.setName(request.getName());
//        currentClient.setLastname(request.getLastname());
//        currentClient.setPatronymic(request.getPatronymic());
//
//        Set<City> citySet = new HashSet<>();
//        for (LinkToCity city : request.getCity()) {
//            City newCity = new City();
//            newCity.setId(city.getId());
//            citySet.add(newCity);
//        }
//        currentClient.setCities(citySet);
//
//        currentClient.getCars().addAll(internalChangeClientCar(request, currentClient));
//        clientRepository.save(currentClient);
    }

//    private Set<Car> internalChangeClientCar(ClientChangeRequest request, Client newClient) {
//        Set<Car> carSet = new HashSet<>();
//        for (CarChangeRequest car : request.getCar()) {
//            Car newCar = new Car();
//            newCar.setId(car.getId());
//            newCar.setClient(newClient);
//            newCar.setName(car.getName());
//            newCar.setRegistrationPlate(car.getRegistrationPlate());
//
//            Mark mark = new Mark();
//            mark.setId(car.getMark().getId());
//            newCar.setMark(mark);
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
