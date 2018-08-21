package com.example.clientsDB.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CityChangeRequest {

    @Size(max = 30)
    private String name;
}
