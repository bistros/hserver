package com.bistros.hauto.server.Infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {
    private final String id;
    private String model;
    private String brand;
}
