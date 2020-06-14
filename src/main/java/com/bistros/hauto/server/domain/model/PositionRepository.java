package com.bistros.hauto.server.domain.model;

import com.bistros.hauto.server.domain.model.search.DistanceSearch;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface PositionRepository {
    List<Pair<String, Position>> all();

    void put(Car id, Position position);

    Position get(Car car);

    List<Position> route(Car id);

    List<DistanceSearch> search(int lat, int lon, int radius);
}
