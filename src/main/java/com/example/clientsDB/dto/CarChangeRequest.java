package com.example.clientsDB.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarChangeRequest {

    private Long id;

    @Size(max = 30)
    private String name;

    @Size(max = 9)
    private String registrationPlate;

    private LinkToMark mark;
}
