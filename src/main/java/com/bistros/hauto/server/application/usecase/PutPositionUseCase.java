package com.bistros.hauto.server.application.usecase;

import com.bistros.hauto.server.application.shared.Request;
import com.bistros.hauto.server.application.shared.Response;
import com.bistros.hauto.server.application.shared.Usecase;
import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import com.bistros.hauto.server.domain.model.PositionRepository;
import com.bistros.hauto.server.domain.service.CarService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PutPositionUseCase implements Usecase<PutPositionUseCase.PutRequest, PutPositionUseCase.PutResponse> {

    private PositionRepository positionRepository;
    private CarService carService;

    public PutPositionUseCase(PositionRepository positionRepository, CarService carService) {
        this.positionRepository = positionRepository;
        this.carService = carService;
    }

    @Override
    public PutResponse apply(PutRequest putRequest) {
        Car car = carService.getCar(putRequest.id); //ID 기반으로 추가적인 Car 정보를 획득해야 할 경우를 표현
        positionRepository.put(car, putRequest.position);
        return new PutResponse(car.getId(), "OK");
    }

    // 데이터를 저장하는 `PutPositionUseCase` 를 위한 request model
    public static class PutResponse implements Response {
        private String carId;
        private String status;

        public PutResponse(String carId, String status) {
            this.carId = carId;
            this.status = status;
        }

        public String getCarId() {
            return carId;
        }

        public String getStatus() {
            return status;
        }
    }

    @Getter
    public static class PutRequest implements Request {
        private final String id;
        private final Position position;
        // Client 에서 생성된 로그가 실제로 서버에 수신 된 시간 ( 네트워크 단절로 이벤트 발생시간은 이벤트 수신 시간과 다르다 )
        private final LocalDateTime receiveTime;

        public PutRequest(String id, Position position) {
            this.id = id;
            this.position = position;
            this.receiveTime = LocalDateTime.now();
        }
    }
}
