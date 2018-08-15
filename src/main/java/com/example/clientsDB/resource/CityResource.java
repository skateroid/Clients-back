package com.example.clientsDB.resource;

import com.example.clientsDB.dto.CityChangeRequest;
import com.example.clientsDB.model.City;
import com.example.clientsDB.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@Api(tags = "city", description = "Города")
public class CityResource {
    private CityService cityService;

    public CityResource(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @ApiOperation("Получить список всех городов")
    public List<City> getAll() {
        return cityService.getAll();
    }

    @GetMapping("/search")
    @ApiOperation("Получить город по названию")
    public List<City> findCityByName(@RequestParam(name = "name") String name) {
        return cityService.findCityByName(name);
    }

    @GetMapping("{id}")
    @ApiOperation("Получить город по id")
    public City findCityById(@PathVariable(name = "id") Long id) {
        return cityService.findCityById(id);
    }

    @PostMapping
    @ApiOperation("Добавить город")
    public City create(@Valid @RequestBody CityChangeRequest request) {
        return cityService.createCity(request);
    }

    @PutMapping("{cityId}")
    @ApiOperation("Внести изменения в существующий город")
    public City update(@PathVariable("cityId") Long cityId,
                       @RequestBody CityChangeRequest request) {
        return cityService.updateCity(cityId, request);
    }

    @DeleteMapping("{cityId}")
    @ApiOperation("Удалить город")
    public void delete(@PathVariable("cityId") Long id) {
        cityService.deleteCity(id);
    }
}
