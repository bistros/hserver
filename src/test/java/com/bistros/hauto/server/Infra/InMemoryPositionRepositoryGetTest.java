package com.bistros.hauto.server.Infra;

import com.bistros.hauto.server.Infra.persistence.InMemoryPositionRepository;
import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryPositionRepositoryGetTest {

    private InMemoryPositionRepository repository;
    private final LocalDateTime LOG_TIME = LocalDateTime.of(2000, Month.JANUARY, 1, 20, 0, 0);

    @Before
    public void initData() {
        repository = new InMemoryPositionRepository();

        repository.put(new Car("1"), new Position(38700000, 128300000, LOG_TIME));
        repository.put(new Car("2"), new Position(38700050, 128300000, LOG_TIME));
        repository.put(new Car("3"), new Position(38800000, 128300000, LOG_TIME));

    }

    @Test
    @DisplayName("단순한 get 테스트")
    public void testGet1() {
        Position expected = repository.get(new Car("1"));
        Position expected2 = repository.get(new Car("2"));
        assertAll(
                () -> assertEquals(expected.getLatitude(), 38700000),
                () -> assertEquals(expected.getLongitude(), 128300000),
                () -> assertEquals(expected2.getLatitude(), 38700050),
                () -> assertEquals(expected2.getLongitude(), 128300000)
        );
    }


}