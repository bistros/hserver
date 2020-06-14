package com.bistros.hauto.server.Infra.rest.service;

import com.bistros.hauto.server.Infra.rest.client.CarRestTemplate;
import com.bistros.hauto.server.Infra.rest.dto.CarDto;
import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRestTemplate carClient;

    @Override
    public Car getCar(String id) {
        CarDto carDto = carClient.getCar(id);
        return new Car(carDto.getId());
    }
}
