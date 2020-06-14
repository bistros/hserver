package com.bistros.hauto.client.gis;

import com.bistros.hauto.common.DIRECTION;
import com.bistros.hauto.common.PositionHelper;
import com.bistros.hauto.server.domain.model.Position;

import java.util.Random;

public class PositionGenerator {

    private static final Random RANDOM = new Random();
    private static final int DIRECTION_CASE_SIZE = DIRECTION.values().length;


    public static Position next(Position position) {
        DIRECTION dir = DIRECTION.values()[RANDOM.nextInt(DIRECTION_CASE_SIZE)];
        int speed = RANDOM.nextInt(70);

        Position nextPosition = PositionHelper.next(position, speed, dir);
        return nextPosition;
    }

}
