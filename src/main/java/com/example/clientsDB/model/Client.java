package com.example.clientsDB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NamedEntityGraph(
        name = "ClientFull",
        attributeNodes = {
                @NamedAttributeNode(value = "cities"),
                @NamedAttributeNode(value = "cars", subgraph = "cars.mark")
        },
        subgraphs = @NamedSubgraph(name = "cars.mark", attributeNodes = @NamedAttributeNode("mark"))
)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ClientIdGenerator")
    @SequenceGenerator(name = "ClientIdGenerator", sequenceName = "client_id_seq")
    private Long id;

    private String name;
    private String lastname;
    private String patronymic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "client_city",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
    private Set<City> cities;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Car> cars;

    @JsonIgnore
    @Formula("lastname || ' ' || name")
    private String fullName;

    @JsonIgnore
    @Formula("name || ' ' || lastname")
    private String reversedFullName;

}
