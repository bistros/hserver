package com.bistros.hauto.server.operation.testdata;

import com.bistros.hauto.common.DIRECTION;
import com.bistros.hauto.common.PositionHelper;
import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import com.linecorp.armeria.internal.shaded.guava.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.bistros.hauto.common.DIRECTION.*;
import static com.bistros.hauto.common.PositionHelper.range;

/**
 * 최초에 테스트용 데이터 생성을 위한 헬퍼
 * T1 , T2, T3 자동차의 약 20-30 개의 데이터를 생성한다.
 */
public class InitlizedTestDataSet {

    final LocalDateTime DEFAULT_DATA_EVENT_TIME = LocalDateTime.of(2020, 6, 20, 10, 00, 00);

    public List<Pair<Car, Position>> car1() {
        Car car = new Car("T1");
        Position basePosition = new Position(3700030, 12700700, DEFAULT_DATA_EVENT_TIME);
        List<Pair<Integer, DIRECTION>> directions = Streams.concat(
                range(3, 30, RIGHT), range(5, 20, UP),
                range(1, 10, RIGHT), range(10, 50, DOWN)
        ).collect(Collectors.toList());

        return PositionHelper.positions(basePosition, directions).stream()
                .map(p -> Pair.of(car, p)).collect(Collectors.toList());

    }

    public List<Pair<Car, Position>> car2() {
        Car car = new Car("T2");
        Position basePosition = new Position(3700030, 12700700, DEFAULT_DATA_EVENT_TIME);
        List<Pair<Integer, DIRECTION>> directions = Streams.concat(
                range(3, 30, DOWN), range(2, 20, RIGHT),
                range(7, 30, RIGHT), range(3, 20, UP)
        ).collect(Collectors.toList());
        return PositionHelper.positions(basePosition, directions).stream()
                .map(p -> Pair.of(car, p)).collect(Collectors.toList());

    }

    public List<Pair<Car, Position>> car3() {
        Car car = new Car("T3");
        Position basePosition = new Position(3700100, 12700730, DEFAULT_DATA_EVENT_TIME);
        List<Pair<Integer, DIRECTION>> directions = Streams.concat(
                range(3, 20, LEFT), range(3, 10, DOWN),
                range(7, 10, RIGHT), range(3, 20, UP)
        ).collect(Collectors.toList());
        return PositionHelper.positions(basePosition, directions).stream()
                .map(p -> Pair.of(car, p)).collect(Collectors.toList());

    }


}
