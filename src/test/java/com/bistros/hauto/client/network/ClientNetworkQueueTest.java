package com.bistros.hauto.client.network;

import com.bistros.hauto.server.domain.model.Position;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

public class ClientNetworkQueueTest {


    @DisplayName("클라이언트 큐 사이즈가 넘어갈")
    @Test
    public void test() {
        ClientNetworkQueue queue = new ClientNetworkQueue(2);
        queue.add(new Position(1,1,null));
//        queue.add(Position.of(1,1,null));


        List<Position> sz = queue.getPositions(3);

        System.out.println(sz);
    }

}