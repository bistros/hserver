package com.bistros.hauto.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
@AllArgsConstructor
public class Position {
    private final int latitude;
    private final int longitude;
    private final LocalDateTime eventTime;
}
