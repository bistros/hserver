package com.bistros.hauto.server.api.v1.http.binder;

import com.bistros.hauto.server.api.v1.http.viewmodel.HistoryViewModel;
import com.bistros.hauto.server.application.usecase.GetHistoryUseCase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HttpHistoryBinder {
    DateTimeFormatter requestTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    public GetHistoryUseCase.HistoryRequest bind(String carId, String startDate, String endDate) {
        return new GetHistoryUseCase.HistoryRequest(
                carId,
                LocalDateTime.parse(startDate, requestTimeFormatter),
                LocalDateTime.parse(endDate, requestTimeFormatter));
    }

    public HistoryViewModel present(GetHistoryUseCase.HistoryResponse response) {
        return new HistoryViewModel(response.getCarId(), response.getRoutes());
    }
}
