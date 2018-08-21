package com.example.clientsDB.service;

import com.example.clientsDB.dto.*;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.model.Car;
import com.example.clientsDB.model.City;
import com.example.clientsDB.model.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceIntTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CityService cityService;

    @Autowired
    private MarkService markService;

    private static Long markId1;

    private static LinkToMark linkToMark;

    private static Long clientId;

    private static boolean initialized;

    private static Set<LinkToCity> linkToCitySet1City;
    private static Set<LinkToCity> linkToCitySet2City;
    private static Set<CarChangeRequest> carRequests1car;
    private static Set<CarChangeRequest> carRequests2car;

    @Before
    public void setUp() {
        if (!initialized) {
            Long cityId1;
            Long cityId2;

            CityChangeRequest city1 = CityChangeRequest.builder()
                    .name("NN")
                    .build();

            CityChangeRequest city2 = CityChangeRequest.builder()
                    .name("kirov")
                    .build();

            cityId1 = cityService.createCity(city1).getId();
            cityId2 = cityService.createCity(city2).getId();

            MarkChangeRequest markChangeRequest = MarkChangeRequest.builder()
                    .name("audi")
                    .build();
            markId1 = markService.createMark(markChangeRequest).getId();

            linkToCitySet2City = new HashSet<>();
            LinkToCity link1 = new LinkToCity();
            link1.setId(cityId1);
            linkToCitySet2City.add(link1);
            LinkToCity link2 = new LinkToCity();
            link2.setId(cityId2);
            linkToCitySet2City.add(link2);

            linkToCitySet1City = new HashSet<>();
            LinkToCity link1city = new LinkToCity();
            link1city.setId(cityId2);
            linkToCitySet1City.add(link1city);

            carRequests1car = new HashSet<>();
            CarChangeRequest car1 = new CarChangeRequest();
            car1.setName("x3");
            car1.setRegistrationPlate("00KKJ22");
            linkToMark = new LinkToMark();
            linkToMark.setId(markId1);
            car1.setMark(linkToMark);
            carRequests1car.add(car1);

            carRequests2car = new HashSet<>();
            carRequests2car.add(car1);
            CarChangeRequest car2 = new CarChangeRequest();
            car2.setName("A5");
            car2.setRegistrationPlate("34VVC111");
            car2.setMark(linkToMark);
            carRequests2car.add(car2);

            initialized = true;
        }
    }

    private void createClient(String name, String lastName, String patronymic, Set<LinkToCity> link2city, Set<CarChangeRequest> carRequest) {
        ClientChangeRequest request = ClientChangeRequest.builder()
                .name(name)
                .lastname(lastName)
                .patronymic(patronymic)
                .cities(link2city)
                .cars(carRequest)
                .build();
        clientId = clientService.createClient(request).getId();
    }

    @Test
    public void testCreateAndGet() {
        clientService.deleteAll();
        createClient("Eugene", "Khokhlin", "Alexandrovich", linkToCitySet2City, carRequests2car);
        Client client = clientService.getClientById(clientId);

        assertThat(client.getName()).isEqualTo("Eugene");
        assertThat(client.getLastname()).isEqualTo("Khokhlin");
        assertThat(client.getPatronymic()).isEqualTo("Alexandrovich");
        assertThat(client.getCities()).hasSize(2);
        Iterator<City> iterator = client.getCities().iterator();
        assertThat(iterator.next().getName()).isEqualTo("NN");
        assertThat(iterator.next().getName()).isEqualTo("kirov");
    }

    @Test
    public void testGetAll() {
        clientService.deleteAll();

        assertThat(clientService.getAll()).hasSize(0);

        createClient("Eugene", "Khokhlin", "Alexandrovich", linkToCitySet2City, carRequests2car);
        createClient("Mozbek", "Ibragimov", "Oglich", linkToCitySet2City, carRequests1car);
        List<Client> clientList = clientService.getAll();

        assertThat(clientList).hasSize(2);
        assertThat(clientList.get(0).getLastname()).isEqualTo("Ibragimov");
    }

    @Test
    public void testUpdateClientWithEmptyCarsId() {
        createClient("Eugene", "Khokhlin", "Alexandrovich", linkToCitySet2City, carRequests2car);
        ClientChangeRequest upRequest = ClientChangeRequest.builder()
                .name("Alex")
                .lastname("Petrov")
                .cities(linkToCitySet1City)
                .cars(carRequests2car)
                .build();
        clientService.updateClient(clientId, upRequest);
        Client clientAfterUpdate = clientService.getClientById(clientId);

        assertThat(clientAfterUpdate.getName()).isEqualTo("Alex");
        assertThat(clientAfterUpdate.getLastname()).isEqualTo("Petrov");
        assertThat(clientAfterUpdate.getPatronymic()).isNullOrEmpty();
        assertThat(clientAfterUpdate.getCities().iterator().next().getName()).isEqualTo("kirov");

        Iterator<Car> iterator = clientAfterUpdate.getCars().iterator();
        Car car1 = iterator.next();
        assertThat(car1.getRegistrationPlate()).isEqualTo("00KKJ22");
        assertThat(car1.getName()).isEqualTo("x3");
        Car car2 = iterator.next();
        assertThat(car2.getRegistrationPlate()).isEqualTo("34VVC111");
        assertThat(car2.getName()).isEqualTo("A5");
    }

    @Test
    public void testUpdateClientAndUpdateCar() {
        createClient("Eugene", "Khokhlin", "Alexandrovich", linkToCitySet2City, carRequests2car);

        assertThat(clientService.getClientById(clientId).getCars()).hasSize(2);

        Set<CarChangeRequest> carChangeRequestSet = new HashSet<>();
        CarChangeRequest carChangeRequest = new CarChangeRequest();
        carChangeRequest.setName("A5");
        carChangeRequest.setRegistrationPlate("34VPP00XX");
        carChangeRequest.setMark(linkToMark);
        carChangeRequest.setId(8L);
        carChangeRequestSet.add(carChangeRequest);

        ClientChangeRequest upRequest = ClientChangeRequest.builder()
                .name("Alex")
                .lastname("Petrov")
                .cities(linkToCitySet1City)
                .cars(carChangeRequestSet)
                .build();
        clientService.updateClient(clientId, upRequest);
        Client clientAfterUpdate = clientService.getClientById(clientId);

        assertThat(clientAfterUpdate.getCars()).hasSize(1);
        assertThat(clientAfterUpdate.getCars().iterator().next().getRegistrationPlate()).isEqualTo("34VPP00XX");
    }

    @Test
    public void testGetClientByName() {
        createClient("Timur", "Noskov", "Alexandrovich", linkToCitySet2City, carRequests2car);
        List<Client> clientList = clientService.getClientByFullName("kov");

        assertThat(clientList.get(0).getLastname()).isEqualTo("Noskov");
    }

    @Test
    public void testDeleteClient() {
        Set<CarChangeRequest> carRequestsDel = new HashSet<>();
        CarChangeRequest car1 = new CarChangeRequest();
        car1.setName("x3");
        car1.setRegistrationPlate("00KKJ22");
        LinkToMark linkToMark = new LinkToMark();
        linkToMark.setId(markId1);
        car1.setMark(linkToMark);

        ClientChangeRequest request = ClientChangeRequest.builder()
                .name("Eugene")
                .lastname("Khokhlin")
                .patronymic("Alexandrovich")
                .cities(linkToCitySet2City)
                .cars(carRequestsDel)
                .build();

        Long clientIdForDel = clientService.createClient(request).getId();

        clientService.deleteClient(clientIdForDel);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> clientService.getClientById(clientIdForDel))
                .withMessage("Client not found");
    }

    @Test
    public void testDeleteNonExistingClient() {
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> clientService.deleteClient(324324L));
    }

    @Test
    public void testGetNonExistingClient() {
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> clientService.getClientById(20000L));
    }

    @Test
    public void testUpdateNonExistingClient() {
        ClientChangeRequest request = ClientChangeRequest.builder()
                .name("Alex")
                .lastname("Petrov")
                .cities(linkToCitySet1City)
                .cars(carRequests2car)
                .build();
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> clientService.updateClient(20000L, request));
    }

    @Test
    public void testUpdateClientWithNonExistingCar() {
        createClient("Timur", "Noskov", "Alexandrovich", linkToCitySet2City, carRequests2car);
        Set<CarChangeRequest> carChangeRequestSet = new HashSet<>();
        CarChangeRequest carChangeRequest = CarChangeRequest.builder()
                .id(65654L)
                .name("TT")
                .registrationPlate("ZZZ12312")
                .mark(linkToMark)
                .build();
        carChangeRequestSet.add(carChangeRequest);
        ClientChangeRequest updateRequest = ClientChangeRequest.builder()
                .name("Timur")
                .lastname("Noskov")
                .patronymic("Alexandrovich")
                .cities(linkToCitySet2City)
                .cars(carChangeRequestSet)
                .build();
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> clientService.updateClient(clientId, updateRequest))
                .withMessage("Car with id: 65654 not found");
    }
}
