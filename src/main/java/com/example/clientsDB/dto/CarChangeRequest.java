package com.example.clientsDB.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;

@Data
public class CarChangeRequest {

    private Long id;

    @Size(max = 30)
    private String name;

    @Size(max = 9)
    private String registrationPlate;

    private LinkToMark mark;
}
