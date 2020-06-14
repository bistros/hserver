package com.bistros.hauto.server.domain.model.search;

import com.bistros.hauto.server.domain.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DistanceSearch {
    private final String carId;
    private final Position position;
    private final int distnce;
}
