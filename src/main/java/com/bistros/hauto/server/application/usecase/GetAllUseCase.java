package com.bistros.hauto.server.application.usecase;

import com.bistros.hauto.server.application.shared.Request;
import com.bistros.hauto.server.application.shared.Response;
import com.bistros.hauto.server.application.shared.Usecase;
import com.bistros.hauto.server.domain.model.CarPosition;
import com.bistros.hauto.server.domain.model.PositionRepository;
import com.bistros.hauto.server.domain.service.CarService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllUseCase implements
        Usecase<GetAllUseCase.NoParameter, GetAllUseCase.GetAllResponse> {

    private final PositionRepository positionRepository;
    private final CarService carService;

    @Override
    public GetAllResponse apply(NoParameter getAllRequest) {
        List<CarPosition> carPositions = positionRepository.all().stream().map(
                c -> new CarPosition(carService.getCar(c.getKey()), c.getValue())
        ).collect(Collectors.toList());
        return new GetAllResponse(carPositions);
    }

    public GetAllResponse apply() {
        return this.apply(NoParameter.Instance);
    }

    public static class NoParameter implements Request {
        static NoParameter Instance = new NoParameter();
    }

    @Getter
    @AllArgsConstructor
    public static class GetAllResponse implements Response {
        private final List<CarPosition> results;
    }
}
