package com.bistros.hauto.server.api.v1.grpc;


import com.bistros.hauto.apiv1.*;
import com.bistros.hauto.server.api.v1.grpc.binder.GetAllBinder;
import com.bistros.hauto.server.api.v1.grpc.binder.GetHistoryBinder;
import com.bistros.hauto.server.api.v1.grpc.binder.GetPositionBinder;
import com.bistros.hauto.server.api.v1.grpc.binder.SearchAroundBinder;
import com.bistros.hauto.server.application.usecase.GetAllUseCase;
import com.bistros.hauto.server.application.usecase.GetHistoryUseCase;
import com.bistros.hauto.server.application.usecase.GetPositionUsecase;
import com.bistros.hauto.server.application.usecase.SearchAroundUserCase;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GrpcServiceImpl extends CarServiceGrpc.CarServiceImplBase {

    private final GetPositionUsecase getUsecase;
    private final GetPositionBinder getPositionBinder;

    private final GetHistoryUseCase historyUseCase;
    private final GetHistoryBinder historyBinder;

    private final SearchAroundUserCase searchAroundUserCase;
    private final SearchAroundBinder searchAroundBinder;

    private final GetAllUseCase allUseCase;
    private final GetAllBinder allBinder;

    @Override
    public void get(GetPositionRequestProto proto,
                    StreamObserver<GetPositionResponseProto> responseObserver) {
        GetPositionUsecase.GetRequest request = getPositionBinder.bind(proto);
        GetPositionUsecase.GetResponse response = this.getUsecase.apply(request);
        GetPositionResponseProto responseProto = getPositionBinder.present(response);

        responseObserver.onNext(responseProto);
        responseObserver.onCompleted();
    }

    @Override
    public void history(GetRouteRequestProto proto,
                        StreamObserver<GetRouteResponseProto> responseObserver) {
        GetHistoryUseCase.HistoryRequest request = historyBinder.bind(proto);
        GetHistoryUseCase.HistoryResponse response = historyUseCase.apply(request);
        GetRouteResponseProto responseProto = historyBinder.present(response);

        responseObserver.onNext(responseProto);
        responseObserver.onCompleted();
    }


    @Override
    public void search(SearchAroundRequestProto proto,
                       StreamObserver<SearchAroundResponseProto> responseObserver) {
        SearchAroundUserCase.SearchRequest request = searchAroundBinder.bind(proto);
        SearchAroundUserCase.SearchResponse response = searchAroundUserCase.apply(request);
        SearchAroundResponseProto responseProto = searchAroundBinder.present(response);

        responseObserver.onNext(responseProto);
        responseObserver.onCompleted();
    }

    @Override
    public void all(NoParam request, StreamObserver<GetPositionResponseListProto> responseObserver) {
        GetAllUseCase.GetAllResponse response = allUseCase.apply();
        GetPositionResponseListProto responseProto = allBinder.present(response);

        responseObserver.onNext(responseProto);
        responseObserver.onCompleted();
    }
}
