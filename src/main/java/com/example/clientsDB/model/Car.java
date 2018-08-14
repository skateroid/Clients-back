package com.example.clientsDB.model;

import lombok.Data;

@Data
public class Car {

    private Long id;
    private String name;
    private String registrationPlate;
//    private Client client;
    private Mark mark;
}
