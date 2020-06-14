package com.bistros.hauto.server.application.usecase;

import com.bistros.hauto.server.Infra.rest.service.CarServiceImpl;
import com.bistros.hauto.server.application.shared.Request;
import com.bistros.hauto.server.application.shared.Response;
import com.bistros.hauto.server.application.shared.Usecase;
import com.bistros.hauto.server.domain.model.PositionRepository;
import com.bistros.hauto.server.domain.model.search.DistanceSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SearchAroundUserCase implements
        Usecase<SearchAroundUserCase.SearchRequest, SearchAroundUserCase.SearchResponse> {

    private PositionRepository repository;
    private CarServiceImpl carService;

    public SearchAroundUserCase(PositionRepository repository, CarServiceImpl carService) {
        this.repository = repository;
        this.carService = carService;
    }

    /**
     * 반경내에 자동차를 찾는 API를 구현해야 하지만 시간상 ^^; for-loop를 이용해서 간단히 구현
     * <p>
     * <p>
     * microservice 아키텍쳐에서 가장 어려운 부분이다.
     * <p>
     * 데이터 분리를 하였을 때 N+1 이나, 데이터 정합성 부분.
     * 데이터를 분리 하지 않았을 경우에은 7억건의 상품정보와 해당하는 리뷰의 정보를 거대한 DB로 온전히 처리할 것인가?
     */

    @Override
    public SearchResponse apply(SearchRequest request) {
        List<DistanceSearch> candidate =
                repository.search(request.getLatitude(), request.getLongitude(), request.getRadius());

        return new SearchResponse(candidate);
    }

    @Getter
    @AllArgsConstructor
    public static class SearchResponse implements Response {
        private final List<DistanceSearch> results;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchRequest implements Request, Serializable {
        private int latitude;
        private int longitude;
        private int radius;
    }
}
