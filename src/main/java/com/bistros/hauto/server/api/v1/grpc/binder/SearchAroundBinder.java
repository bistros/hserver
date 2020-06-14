package com.bistros.hauto.server.api.v1.grpc.binder;

import com.bistros.hauto.apiv1.GetPositionResponseProto;
import com.bistros.hauto.apiv1.SearchAroundRequestProto;
import com.bistros.hauto.apiv1.SearchAroundResponseProto;
import com.bistros.hauto.server.application.shared.Binder;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.SearchAroundUserCase;
import com.bistros.hauto.server.domain.model.search.DistanceSearch;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class SearchAroundBinder extends GrpcTypeConverter implements
        Presenter<SearchAroundUserCase.SearchResponse, SearchAroundResponseProto>,
        Binder<SearchAroundUserCase.SearchRequest, SearchAroundRequestProto> {


    @Override
    public SearchAroundUserCase.SearchRequest bind(SearchAroundRequestProto proto) {
        return new SearchAroundUserCase.SearchRequest(proto.getLatitude(), proto.getLongitude(), proto.getRadius());
    }

    @Override
    public SearchAroundResponseProto present(SearchAroundUserCase.SearchResponse responseModel) {
        List<SearchAroundResponseProto.SearchResponse> list =
                responseModel.getResults().stream()
                        .map(this::buildSearchResponse).collect(toList());
        return SearchAroundResponseProto.newBuilder().addAllResults(list).build();
    }

    private SearchAroundResponseProto.SearchResponse buildSearchResponse(DistanceSearch distanceSearch) {
        return SearchAroundResponseProto.SearchResponse.newBuilder()
                .setDistance(distanceSearch.getDistnce())
                .setCarPosition(buildCarPosition(distanceSearch)).build();
    }

    private GetPositionResponseProto buildCarPosition(DistanceSearch distanceSearch) {
        return GetPositionResponseProto.newBuilder()
                .setId(distanceSearch.getCarId())
                .setPosition(toPositionProto(distanceSearch.getPosition())).build();
    }

}
