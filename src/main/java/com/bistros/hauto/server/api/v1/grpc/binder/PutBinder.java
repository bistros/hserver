package com.bistros.hauto.server.api.v1.grpc.binder;

import com.bistros.hauto.apiv1.CarPositionWriteRequestProto;
import com.bistros.hauto.apiv1.CarPositionWriteResponseProto;
import com.bistros.hauto.server.application.shared.Binder;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.PutPositionUseCase;
import org.springframework.stereotype.Component;

@Component
public class PutBinder extends GrpcTypeConverter implements
        Binder<PutPositionUseCase.PutRequest, CarPositionWriteRequestProto>,
        Presenter<PutPositionUseCase.PutResponse, CarPositionWriteResponseProto> {

    @Override
    public PutPositionUseCase.PutRequest bind(CarPositionWriteRequestProto proto) {
        return new PutPositionUseCase.PutRequest(
                proto.getId(), toPosition(proto.getPosition()));
    }

    @Override
    public CarPositionWriteResponseProto present(PutPositionUseCase.PutResponse response) {
        return CarPositionWriteResponseProto.newBuilder()
                .setId(response.getCarId())
                .setResult(response.getStatus())
                .build();
    }


}
