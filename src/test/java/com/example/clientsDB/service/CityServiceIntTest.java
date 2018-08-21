package com.example.clientsDB.service;

import com.example.clientsDB.dto.CityChangeRequest;
import com.example.clientsDB.exception.EntityNotFoundException;
import com.example.clientsDB.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityServiceIntTest {

    @Autowired
    private CityService cityService;


    @Test
    public void testCreateAndGetCity() {
        CityChangeRequest request = new CityChangeRequest();
        request.setName("Bor");
        City city = cityService.createCity(request);
        City city2 = cityService.findCityById(city.getId());
        assertThat(city2.getName()).isEqualTo("Bor");
    }

    @Test
    public void testUpdateCity() {
        CityChangeRequest request = new CityChangeRequest();
        request.setName("Ekb");
        Long id = cityService.createCity(request).getId();

        request.setName("NY");
        cityService.updateCity(id, request);

        assertThat(cityService.findCityById(id).getName()).isEqualTo("NY");
    }

    @Test
    public void testDeleteCity() {
        CityChangeRequest request = new CityChangeRequest();
        request.setName("Izumrudsk");
        Long id = cityService.createCity(request).getId();

        cityService.deleteCity(id);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> cityService.findCityById(id))
                .withMessage(String.format("CityEntity with id: %d not found", id));
    }

    @Test
    public void testGetAll() {
        CityChangeRequest request = new CityChangeRequest();
        request.setName("Krasnoyarsk");
        Long id1 = cityService.createCity(request).getId();
        request.setName("Krasnodar");
        Long id2 = cityService.createCity(request).getId();

        assertThat(cityService.getAll()).isNotEmpty();

        City city1 = cityService.getAll().stream()
                .filter(prod -> prod.getId().equals(id1))
                .findFirst().orElseThrow(AssertionError::new);
        assertThat(city1.getName()).isEqualTo("Krasnoyarsk");

        City city2 = cityService.getAll().stream()
                .filter(prod -> prod.getId().equals(id2))
                .findFirst().orElseThrow(AssertionError::new);
        assertThat(city2.getName()).isEqualTo("Krasnodar");
    }

    @Test
    public void testGetCityByName() {
        CityChangeRequest request = CityChangeRequest.builder()
                .name("Ivanovo")
                .build();
        String cityName = cityService.createCity(request).getName();

        assertThat(cityService.findCityByName(cityName).stream()
                .filter(city -> city.getName().equals(cityName))
                .findFirst()
                .orElseThrow(AssertionError::new).getName())
                .isEqualTo("Ivanovo");
    }

    @Test
    public void testGetNonExistingCity() {
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> cityService.findCityById(20000L));
    }

    @Test
    public void testUpdateNonExistingMark() {
        CityChangeRequest request = CityChangeRequest.builder()
                .name("Paris")
                .build();
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> cityService.updateCity(20000L, request));
    }

    @Test
    public void testDeleteCityById() {
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> cityService.deleteCity(2000000L));
    }
}
