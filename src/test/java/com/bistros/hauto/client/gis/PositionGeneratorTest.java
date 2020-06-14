package com.bistros.hauto.client.gis;

import com.bistros.hauto.server.domain.model.Position;
import org.junit.Test;

import java.time.LocalDateTime;

public class PositionGeneratorTest {


    @Test
    public void asd() {
        PositionGenerator generator = new PositionGenerator();
        Position position = new Position(3700000,12700000, LocalDateTime.now());

        for(int i= 1; i <= 10; i++) {
            position = generator.next(position);
            System.out.println(position);
        }
    }

}