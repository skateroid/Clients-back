package com.example.clientsDB.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "client")
@NamedEntityGraph(
        name = "ClientFull",
        attributeNodes = {
                @NamedAttributeNode(value = "cities"),
                @NamedAttributeNode(value = "cars", subgraph = "cars.mark")
        },
        subgraphs = @NamedSubgraph(name = "cars.mark", attributeNodes = @NamedAttributeNode("mark"))
)
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ClientIdGenerator")
    @SequenceGenerator(name = "ClientIdGenerator", sequenceName = "client_id_seq")
    private Long id;

    private String name;
    private String lastname;
    private String patronymic;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    @JoinTable(name = "client_city",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "city_id", referencedColumnName = "id"))
    private Set<CityEntity> cities;

    @OrderBy("registrationPlate ASC")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CarEntity> cars;

    @JsonIgnore
    @Formula("lastname || ' ' || name")
    private String fullName;

    @JsonIgnore
    @Formula("name || ' ' || lastname")
    private String reversedFullName;

}
