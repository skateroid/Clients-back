package com.example.clientsDB.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "mark")
public class MarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MarkIdGenerator")
    @SequenceGenerator(name = "MarkIdGenerator", sequenceName = "mark_id_seq")
    private Long id;
    private String name;

}
