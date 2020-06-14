package com.bistros.hauto.common;

import com.bistros.hauto.server.domain.model.Position;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PositionHelper {

    /**
     * 최초의 위치와 속도,이동방향으로 Position 의 List를 생성한다. (즉, 차량의 이동 정보를 생성)
     *
     * @param position 최초의 위치
     * @param dirs     List(속도, 이동방향)
     * @return
     */
    public static List<Position> positions(Position position, List<Pair<Integer, DIRECTION>> dirs) {
        Position point = position;

        List<Position> data = new ArrayList<>();
        data.add(point);


        for (Pair<Integer, DIRECTION> speedAndDir : dirs) {
            point = PositionHelper.next(point, speedAndDir.getLeft(), speedAndDir.getRight());
            data.add(point);

        }
        return data;
    }

    /**
     * 현재 위경도, 속도, 방향을 가지고 1초뒤의 위경도를 반환한다
     *
     * @param current 현재 위경도
     * @param speed   차량의 속도 m/s
     * @return
     */
    public static Position next(Position current, int speed, DIRECTION direction) {
        int lat = current.getLatitude();
        int lon = current.getLongitude();

        // 속도 기반으로 위경도의 변화량을 구한다.
        // 초속 10 m/s  = 시속 36km = 초당 0.0001   -> speed:10 -> step:10
        // 초속 5 m/s = 시속 18km = 초당 위경도 0.00005 -> speed:5 -> step:5
        int step = speed;

        switch (direction) {
            case UP:
                lat += step;
            case DOWN:
                lat -= step;
            case LEFT:
                lon -= step;
            case RIGHT:
                lon += step;
        }
        return new Position(lat, lon,LocalDateTime.now() );
    }

    /**
     * 속도, 방향을 가지고 count 횟수 만큼 Stream 을 생성한다.
     *
     * @param count 해당 방향으로 이동할 시간 (초)
     * @param speed 속도 (m/s)
     * @param dir   방향
     * @return
     */
    public static Stream<Pair<Integer, DIRECTION>> range(int count, int speed, DIRECTION dir) {
        return IntStream.range(1, count).mapToObj(c -> ImmutablePair.of(speed, dir));
    }

}
