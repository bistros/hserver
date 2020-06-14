package com.bistros.hauto.server.Infra.rest.client;

import com.bistros.hauto.server.Infra.rest.dto.CarDto;
import org.springframework.stereotype.Component;

@Component
public class CarRestTemplate {
    public CarDto getCar(String id) {
        return new CarDto(id, "", "");
    }
}
