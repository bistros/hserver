package com.bistros.hauto.server.Infra.persistence.extension;

import com.bistros.hauto.server.domain.model.Position;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * com.github.grumlimited:geocalc 라이브러리 사용 방법을 위한 테스트 코드입니다.
 */
public class GeospatialExtensionTest {

    private final GeospatialExtension extension = new GeospatialExtension();
    private final double epsilon = 0.00001d;

    @Test
    @DisplayName("com.github.grumlimited:geocalc 을 이용하여 위경도를 Point 로 변경하고 거리를 계산해본다")
    public void how_to_learn_make_point() {
        Point point1 = Point.at(Coordinate.fromDegrees(37.0d), Coordinate.fromDegrees(127.0d));
        Point point2 = Point.at(Coordinate.fromDegrees(38.0d), Coordinate.fromDegrees(127.0d));
        double metersGcdType = EarthCalc.gcdDistance(point1, point2);
        double metersVincentType = EarthCalc.vincentyDistance(point1, point2);
    }

    @Test
    @DisplayName("INT 타입의 위경도가 제대로 Point로 변경되는지 테스트")
    public void toPointTest() {
        Position position = new Position(3700000, 12700000, null);

        Point geoPoint1 = extension.toPoint(position);

        assertAll(
                () -> assertEquals(geoPoint1.latitude, 37.00000, epsilon),
                () -> assertEquals(geoPoint1.longitude, 127.00000, epsilon)
        );


    }

}