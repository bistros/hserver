package com.bistros.hauto.server.api.v1.grpc.binder;

import com.bistros.hauto.apiv1.GetPositionResponseListProto;
import com.bistros.hauto.apiv1.GetPositionResponseProto;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.GetAllUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllBinder
        implements Presenter<GetAllUseCase.GetAllResponse, GetPositionResponseListProto> {


    @Override
    public GetPositionResponseListProto present(GetAllUseCase.GetAllResponse response) {
        List<GetPositionResponseProto> lists =
                response.getResults().stream()
                        .map(c -> GetPositionResponseProto.newBuilder()
                                .setPosition(GrpcTypeConverter.toPositionProto(c.getPosition()))
                                .setId(c.getCar().getId())
                                .build())
                        .collect(Collectors.toList());

        return GetPositionResponseListProto.newBuilder().addAllResults(lists).build();
    }
}
