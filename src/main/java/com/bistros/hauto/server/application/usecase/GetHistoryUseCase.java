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

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class GetHistoryUseCase implements
        Usecase<GetHistoryUseCase.HistoryRequest, GetHistoryUseCase.HistoryResponse> {

    private final PositionRepository positionRepository;
    private final CarServiceImpl carService;

    @Override
    public HistoryResponse apply(HistoryRequest requestModel) {
        Car car = carService.getCar(requestModel.getCarId());
        List<Position> routes = positionRepository.route(car);
        return new HistoryResponse(car.getId(), routes);
    }

    @Getter
    @AllArgsConstructor
    public static class HistoryResponse implements Response {
        private final String carId;
        private final List<Position> routes;
    }

    @Getter
    @AllArgsConstructor
    public static class HistoryRequest implements Request {
        private final String carId;
        private final LocalDateTime startTime;
        private final LocalDateTime endTime;
    }
}
