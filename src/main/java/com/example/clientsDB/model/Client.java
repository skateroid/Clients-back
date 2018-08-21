package com.example.clientsDB.model;

import java.util.List;
import lombok.Data;

import java.util.Set;

@Data
public class Client {

    private Long id;
    private String name;
    private String lastname;
    private String patronymic;
    private List<City> cities;
    private List<Car> cars;
}
