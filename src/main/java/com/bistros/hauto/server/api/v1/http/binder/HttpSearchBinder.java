package com.bistros.hauto.server.api.v1.http.binder;

import com.bistros.hauto.server.api.v1.http.viewmodel.SearchViewModel;
import com.bistros.hauto.server.application.shared.Presenter;
import com.bistros.hauto.server.application.usecase.SearchAroundUserCase;
import org.springframework.stereotype.Component;

@Component
public class HttpSearchBinder implements
        Presenter<SearchAroundUserCase.SearchResponse, SearchViewModel> {

    @Override
    public SearchViewModel present(SearchAroundUserCase.SearchResponse response) {
        return new SearchViewModel(response.getResults());
    }

    public SearchAroundUserCase.SearchRequest bind(int lat, int lon, int radius) {
        return new SearchAroundUserCase.SearchRequest(lat, lon, radius);
    }

}
