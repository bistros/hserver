package com.bistros.hauto.client.model;

import com.bistros.hauto.client.gis.PositionGenerator;
import com.bistros.hauto.client.network.ClientNetwork;
import com.bistros.hauto.server.domain.model.Position;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class AutoMobileClient implements Runnable {

    private final ClientNetwork network;
    private Position current = new Position(3700000, 12700000, LocalDateTime.now());
    private int moving_cap_time = 1;

    public AutoMobileClient(Simulrator simulrator, int time) {
        this.moving_cap_time = time;
        this.network = new ClientNetwork(simulrator);
        Thread networkThread = new Thread(network);
        networkThread.setName("Network-Thread-" + simulrator.getId());
        networkThread.start();
    }

    /**
     * 1초마다 위치 정보를 생성하여 네트워크 전송을 요청한다
     */
    @Override
    public void run() {
        while (true) {
            current = PositionGenerator.next(current);
            network.store(current);
            try {
                TimeUnit.SECONDS.sleep(moving_cap_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
