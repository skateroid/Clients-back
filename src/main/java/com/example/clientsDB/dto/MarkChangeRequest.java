package com.example.clientsDB.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MarkChangeRequest {

    @Size(max = 30)
    private String name;
}
