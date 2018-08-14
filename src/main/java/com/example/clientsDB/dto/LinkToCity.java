package com.example.clientsDB.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LinkToCity {

    @NotNull
    private Long id;
}
