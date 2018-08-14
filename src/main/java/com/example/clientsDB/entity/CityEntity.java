package com.example.clientsDB.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "city")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CityIdGenerator")
    @SequenceGenerator(name = "CityIdGenerator", sequenceName = "city_id_seq")
    private Long id;

    private String name;

}
