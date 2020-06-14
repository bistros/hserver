package com.bistros.hauto.client.network;

import com.bistros.hauto.server.domain.model.Position;
import com.google.common.collect.EvictingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/**
 * 여러가지 이유로 Client 는 항상 안정적인 통신을 할 수 없다.
 * 로그를 송신 하지 못 할 경우 일정시간 client 의 disk 에 저장을 하고 통신이 복구되면 그 때 다시 데이터를 보내야한다.
 * <p>
 * 아래의 구현은 In-Memory 에서 1시간치의 데이터만을 저장하는 단순한 형태이지만.
 * 실제로는 블랙박스 처럼  전원이 차단되거나, 오랜기간 지나도 삭제되지 않는 저장소를 이용해야 한다.
 * <p>
 * <p>
 * 통신 두절 상태에는 해당 큐에 위치 정보를 기록하고,
 * 통신이 복구되면 천천히 속도를 제어하면서 로그를 서버쪽으로 송신하는 작업을 진행한다.
 */
public class ClientNetworkQueue {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final int queueSize;
    private final Queue<Position> clientQueue;

    public ClientNetworkQueue(int queueSize) {
        this.queueSize = queueSize;
        this.clientQueue = EvictingQueue.create(queueSize);
    }

    public void add(Position position) {
        if (queueSize <= clientQueue.size()) {
            logger.warn("Client Queue에 저장된 데이터가 저장범위를 벗어납니다. 오래된 위치 데이터를 삭제하겠습니다  " + clientQueue.poll());
        }
        clientQueue.add(position);
    }

    /* 큐에서 size 만큼의 데이터를 획득한다 */
    public List<Position> getPositions(int size) {
        int actual = Math.min(size, clientQueue.size());
        return range(0, actual).mapToObj(c -> clientQueue.poll()).collect(toList());
    }


}
