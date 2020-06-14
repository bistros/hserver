package com.bistros.hauto.server.api.v1.http.viewmodel;

import com.bistros.hauto.server.domain.model.CarPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllViewModel implements ViewModel {
    List<CarPosition> results;
}
