package com.bistros.hauto.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarPosition {
    private final Car car;
    private final Position position;
}
