package com.bistros.hauto.server.Infra;

import com.bistros.hauto.server.Infra.persistence.InMemoryPositionRepository;

import java.time.LocalDateTime;
import java.time.Month;

public class InMemoryPositionRepositoryRouteTest {

    private InMemoryPositionRepository repository;
    private final LocalDateTime LOG_TIME = LocalDateTime.of(2000, Month.JANUARY, 1, 20, 0, 0);


}