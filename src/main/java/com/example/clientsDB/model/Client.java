package com.example.clientsDB.model;

import lombok.Data;

import java.util.Set;

@Data
public class Client {

    private Long id;
    private String name;
    private String lastname;
    private String patronymic;
    private Set<City> cities;
    private Set<Car> cars;
}
