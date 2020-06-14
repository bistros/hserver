package com.bistros.hauto.server.Infra.persistence;

import com.bistros.hauto.server.Infra.persistence.extension.GeospatialExtension;
import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import com.bistros.hauto.server.domain.model.PositionRepository;
import com.bistros.hauto.server.domain.model.search.DistanceSearch;
import com.grum.geocalc.Point;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryPositionRepository extends GeospatialExtension implements PositionRepository {

    Map<String, Position> latestPosition = new HashMap<>();
    Map<String, List<Position>> histories = new HashMap<>();

    @Override
    public Position get(Car car) {
        String carId = car.getId();
        Position position = latestPosition.get(carId);
        return position;
    }


    @Override
    public List<Pair<String, Position>> all() {
        return latestPosition.entrySet().stream()
                .map(c -> Pair.of(c.getKey(), c.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void put(final Car car, final Position position) {
        String carId = car.getId();

        latestPosition.put(carId, position);

        // List<Position> 현재 버그이다. 반드시 고칠 부분
        histories.putIfAbsent(carId, new ArrayList<>());
        histories.get(car.getId()).add(position);

    }


    @Override
    public List<Position> route(Car car) {
        String carId = car.getId();
        List<Position> routes = histories.get(carId);
        return routes;
    }

    @Override
    public List<DistanceSearch> search(int lat, int lon, int radius) {
        final Point base = toPoint(lat, lon);

        List<DistanceSearch> results = latestPosition.entrySet().parallelStream()
                .map(c -> Triple.of(c.getKey(), c.getValue(), distance(base, toPoint(c.getValue())))) //거리를 계산한다
                .filter(tri -> tri.getRight() < radius)   //Radius 내의 데이터만 필터링
                .map(tri -> new DistanceSearch(tri.getLeft(), tri.getMiddle(), tri.getRight())) // 검색 결과 객체 생성
                .collect(Collectors.toList());
        return results;
    }


}
