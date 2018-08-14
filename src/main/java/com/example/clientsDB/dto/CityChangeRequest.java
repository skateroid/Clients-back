package com.example.clientsDB.dto;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class CityChangeRequest {

//    @Size(max = 30)
    private String name;
}
