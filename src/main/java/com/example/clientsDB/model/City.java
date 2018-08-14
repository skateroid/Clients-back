package com.example.clientsDB.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CityIdGenerator")
    @SequenceGenerator(name = "CityIdGenerator", sequenceName = "city_id_seq")
    private Long id;

    private String name;

}
