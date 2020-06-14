package com.bistros.hauto.server.api.v1.http;

import com.bistros.hauto.server.api.v1.http.binder.HttpGetBinder;
import com.bistros.hauto.server.api.v1.http.binder.HttpHistoryBinder;
import com.bistros.hauto.server.api.v1.http.binder.HttpSearchBinder;
import com.bistros.hauto.server.api.v1.http.viewmodel.GetViewModel;
import com.bistros.hauto.server.api.v1.http.viewmodel.HistoryViewModel;
import com.bistros.hauto.server.api.v1.http.viewmodel.SearchViewModel;
import com.bistros.hauto.server.application.usecase.GetHistoryUseCase;
import com.bistros.hauto.server.application.usecase.GetPositionUsecase;
import com.bistros.hauto.server.application.usecase.SearchAroundUserCase;
import com.linecorp.armeria.server.annotation.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
public class HttpServiceImpl {

    private final GetPositionUsecase getUsecase;
    private final HttpGetBinder getBinder;

    private final SearchAroundUserCase searchAroundUserCase;
    private final HttpSearchBinder searchAroundBinder;

    private final GetHistoryUseCase historyUseCase;
    private final HttpHistoryBinder historyBinder;

    @Get("/car/{id}")
    @ProducesJson
    public GetViewModel get(@Param String id) {
        GetPositionUsecase.GetRequest request = getBinder.bind(id);
        GetPositionUsecase.GetResponse response = getUsecase.apply(request);
        return getBinder.present(response);
    }

    @Get("/search")
    @ProducesJson
    public SearchViewModel search(@Param int lat,
                                  @Param int lon,
                                  @Param int radius) {
        SearchAroundUserCase.SearchRequest request = searchAroundBinder.bind(lat, lon, radius);
        SearchAroundUserCase.SearchResponse response = searchAroundUserCase.apply(request);
        return searchAroundBinder.present(response);
    }

    @Get("/history")
    @ProducesJson
    public HistoryViewModel history(@Param String id,
                                    @Param @Default("20200101-000000") @Description("경로 탐색 시작 시각 ex)20200101-010000") String startDate,
                                    @Param @Default("20201231-000000") @Description("경로 탐색 종료 시각 ex)20200101-010000") String endDate) {
        GetHistoryUseCase.HistoryRequest request = historyBinder.bind(id, startDate, endDate);
        GetHistoryUseCase.HistoryResponse response = historyUseCase.apply(request);
        return historyBinder.present(response);
    }

    //HTTP protocol 에서 PUT 은 지원 안함
}
