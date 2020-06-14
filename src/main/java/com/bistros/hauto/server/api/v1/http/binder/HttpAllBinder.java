package com.bistros.hauto.server.api.v1.http.binder;

import com.bistros.hauto.server.api.v1.http.viewmodel.GetAllViewModel;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.GetAllUseCase;
import org.springframework.stereotype.Component;

@Component
public class HttpAllBinder implements Presenter<GetAllUseCase.GetAllResponse, GetAllViewModel> {
    @Override
    public GetAllViewModel present(GetAllUseCase.GetAllResponse response) {
        return new GetAllViewModel(response.getResults());
    }
}
