package com.bistros.hauto.server.application.usecase;

import com.bistros.hauto.server.Infra.rest.service.CarServiceImpl;
import com.bistros.hauto.server.application.shared.Request;
import com.bistros.hauto.server.application.shared.Response;
import com.bistros.hauto.server.application.shared.Usecase;
import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import com.bistros.hauto.server.domain.model.PositionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;


@Service
public class GetPositionUsecase implements
        Usecase<GetPositionUsecase.GetRequest, GetPositionUsecase.GetResponse> {

    private final PositionRepository positionRepository;
    private final CarServiceImpl carService;

    public GetPositionUsecase(PositionRepository positionRepository, CarServiceImpl carService) {
        this.positionRepository = positionRepository;
        this.carService = carService;
    }

    @Override
    public GetResponse apply(GetRequest getRequestModel) {
        Car car = carService.getCar(getRequestModel.getCarId());
        Position position = positionRepository.get(car);
        return new GetResponse(car, position);
    }

    @Getter
    @AllArgsConstructor
    public static class GetResponse implements Response {
        private final Car car;
        private final Position position;
    }

    @Getter
    @AllArgsConstructor
    public static class GetRequest implements Request {
        private final String carId;
    }

}

