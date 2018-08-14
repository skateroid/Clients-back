package com.example.clientsDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CarIdGenerator")
    @SequenceGenerator(name = "CarIdGenerator", sequenceName = "car_id_seq")
    private Long id;

    private String name;

    private String registrationPlate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private ClientEntity client;

    @ManyToOne
    private MarkEntity mark;

}
