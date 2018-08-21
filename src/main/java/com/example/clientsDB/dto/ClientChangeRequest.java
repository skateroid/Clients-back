package com.example.clientsDB.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientChangeRequest {
    @Size(max = 30)
    private String name;

    @Size(max = 30)
    private String lastname;

    @Size(max = 30)
    private String patronymic;

    private Set<LinkToCity> cities;

    private Set<CarChangeRequest> cars;
}
