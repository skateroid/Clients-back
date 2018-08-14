package com.example.clientsDB.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MarkIdGenerator")
    @SequenceGenerator(name = "MarkIdGenerator", sequenceName = "mark_id_seq")
    private Long id;
    private String name;

}
