package com.bistros.hauto.server.api.v1.grpc.binder;

import com.bistros.hauto.apiv1.GetPositionRequestProto;
import com.bistros.hauto.apiv1.GetPositionResponseProto;
import com.bistros.hauto.server.application.shared.Binder;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.GetPositionUsecase;
import org.springframework.stereotype.Component;

import static com.bistros.hauto.server.api.v1.grpc.binder.GrpcTypeConverter.toPositionProto;

@Component
public class GetPositionBinder
        implements Binder<GetPositionUsecase.GetRequest, GetPositionRequestProto>,
        Presenter<GetPositionUsecase.GetResponse, GetPositionResponseProto> {

    @Override
    public GetPositionUsecase.GetRequest bind(GetPositionRequestProto proto) {
        return new GetPositionUsecase.GetRequest(proto.getId());
    }

    // 자동차의 현재 위치결과인 @GetResponseModel 를 ProtoBuf 형태로 변경합니다
    @Override
    public GetPositionResponseProto present(GetPositionUsecase.GetResponse response) {
        return GetPositionResponseProto.newBuilder()
                .setId(response.getCar().getId())
                .setPosition(toPositionProto(response.getPosition()))
                .build();
    }
}
