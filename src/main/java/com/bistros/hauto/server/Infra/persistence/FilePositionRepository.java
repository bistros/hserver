package com.bistros.hauto.server.Infra.persistence;

import com.bistros.hauto.server.domain.model.Car;
import com.bistros.hauto.server.domain.model.Position;
import com.bistros.hauto.server.domain.model.PositionRepository;
import com.bistros.hauto.server.domain.model.search.DistanceSearch;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 기본적인 로직은 InMemory Repository 에서 처리됩니다.
 * 하지만 HAUto Server 가 갑작스럽게 중지가된다면 모든 데이터를 날라갑니다.
 * 요구사항에서 DB 는 사용불가, File System 은 사용 가능하다 라고 하였으므로
 * Delegate Pattern 을 통해 InMemory 에 저장을 할 때 FileSystem에도 저장되도록 합니다.
 */
public class FilePositionRepository implements PositionRepository {

    final PositionRepository delgate;

    public FilePositionRepository(PositionRepository delgate) {
        this.delgate = delgate;
    }

    @Override
    public List<Pair<String, Position>> all() {
        return this.delgate.all();
    }

    @Override
    public void put(Car car, Position position) {
        this.delgate.put(car, position);
        // TODO 파일에 저장해야함
    }

    @Override
    public Position get(Car car) {
        return this.delgate.get(car);
    }

    @Override
    public List<Position> route(Car car) {
        return this.delgate.route(car);
    }

    @Override
    public List<DistanceSearch> search(int lat, int lon, int radius) {
        return this.delgate.search(lat, lon, radius);
    }
}
