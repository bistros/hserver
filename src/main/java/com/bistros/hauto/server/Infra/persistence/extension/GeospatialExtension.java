package com.bistros.hauto.server.Infra.persistence.extension;

import com.bistros.hauto.server.domain.model.Position;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import static com.grum.geocalc.Coordinate.fromDegrees;

/**
 * MongoDB 좌표 관련 연산이나, MySQL의 Json Path를 지원하는 것처럼 ㄹ
 * In-Memory-Position-Repository에 좌표를 기반 계리 계산 하는 기능을 추가하기 위한 클래스
 */
public class GeospatialExtension {

    // law of cosines
    protected int distance(Point base, Point target) {
        return (int) EarthCalc.gcdDistance(base, target);
    }

    protected int distance(Point base, Position position) {
        Point target = toPoint(position);
        return distance(base, position);
    }

    protected double toDgree(int c) {
        return c / 100000d;
    }

    protected Point toPoint(int lat, int lon) {
        return Point.at(fromDegrees(toDgree(lat)), fromDegrees(toDgree(lon)));
    }

    protected Point toPoint(Position position) {
        return toPoint(position.getLatitude(), position.getLongitude());
    }


}
