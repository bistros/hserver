package com.bistros.hauto.server.api.v1.grpc;

import com.bistros.hauto.apiv1.CarPositionWriteRequestProto;
import com.bistros.hauto.apiv1.CarPositionWriteResponseProto;
import com.bistros.hauto.apiv1.PutServiceGrpc;
import com.bistros.hauto.server.api.v1.grpc.binder.PutBinder;
import com.bistros.hauto.server.application.usecase.PutPositionUseCase;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PutServiceImpl extends PutServiceGrpc.PutServiceImplBase {

    private final PutPositionUseCase putUseCase;
    private final PutBinder putBinder;

    @Override
    public void put(CarPositionWriteRequestProto proto,
                    StreamObserver<CarPositionWriteResponseProto> responseObserver) {
        PutPositionUseCase.PutRequest request = putBinder.bind(proto);
        PutPositionUseCase.PutResponse responseModel = this.putUseCase.apply(request);
        CarPositionWriteResponseProto responseProto = putBinder.present(responseModel);

        responseObserver.onNext(responseProto);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CarPositionWriteRequestProto> putStream(
            StreamObserver<CarPositionWriteResponseProto> responseObserver) {

        return new StreamObserver<CarPositionWriteRequestProto>() {
            @Override
            public void onNext(CarPositionWriteRequestProto value) {
                PutPositionUseCase.PutRequest request = putBinder.bind(value);
                PutPositionUseCase.PutResponse responseModel = putUseCase.apply(request);
                CarPositionWriteResponseProto responseProto = putBinder.present(responseModel);
                responseObserver.onNext(responseProto);
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
