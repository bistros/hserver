package com.bistros.hauto.server.api.v1.http.viewmodel;

import com.bistros.hauto.server.domain.model.search.DistanceSearch;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchViewModel implements ViewModel {
    private final List<DistanceSearch> results;
}
