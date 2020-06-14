package com.bistros.hauto.server.api.v1.http.binder;

import com.bistros.hauto.server.api.v1.http.viewmodel.GetViewModel;
import com.bistros.hauto.server.application.shared.Binder;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.GetPositionUsecase;
import org.springframework.stereotype.Component;

@Component
public class HttpGetBinder implements
        Binder<GetPositionUsecase.GetRequest, String>,
        Presenter<GetPositionUsecase.GetResponse, GetViewModel> {


    @Override
    public GetPositionUsecase.GetRequest bind(String carId) {
        return new GetPositionUsecase.GetRequest(carId);
    }

    @Override
    public GetViewModel present(GetPositionUsecase.GetResponse getResponse) {
        return new GetViewModel(
                getResponse.getCar(),
                getResponse.getPosition()
        );
    }
}
