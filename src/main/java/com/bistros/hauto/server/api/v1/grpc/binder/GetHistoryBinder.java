package com.bistros.hauto.server.api.v1.grpc.binder;

import com.bistros.hauto.apiv1.GetRouteRequestProto;
import com.bistros.hauto.apiv1.GetRouteResponseProto;
import com.bistros.hauto.server.application.shared.Binder;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.GetHistoryUseCase;
import org.springframework.stereotype.Component;

/**
 * 자동차의 이동경로 결과인 @GetRouteResponse 를 ProtoBuf 형태로 변경합니다
 */
@Component
public class GetHistoryBinder extends GrpcTypeConverter implements
        Binder<GetHistoryUseCase.HistoryRequest, GetRouteRequestProto>,
        Presenter<GetHistoryUseCase.HistoryResponse, GetRouteResponseProto> {


    @Override
    public GetHistoryUseCase.HistoryRequest bind(GetRouteRequestProto proto) {
        return new GetHistoryUseCase.HistoryRequest(proto.getId(),
                toLocalDateTime(proto.getStartTime()),
                toLocalDateTime(proto.getEndTime()));
    }

    @Override
    public GetRouteResponseProto present(GetHistoryUseCase.HistoryResponse response) {
        return GetRouteResponseProto.newBuilder()
                .setId(response.getCarId())
                .addAllPosition(toPositionProto(response.getRoutes()))
                .build();
    }

}
