package com.bistros.hauto.server.api.v1.http.viewmodel;

import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetViewModel implements ViewModel{
    private final Car car;
    private final Position position;
}
