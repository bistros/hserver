package com.bistros.hauto.client.network;

import com.bistros.hauto.apiv1.CarPositionWriteRequestProto;
import com.bistros.hauto.apiv1.PositionLogProto;
import com.bistros.hauto.apiv1.PutServiceGrpc;
import com.bistros.hauto.client.model.Simulrator;
import com.bistros.hauto.server.domain.model.Position;
import com.google.protobuf.Timestamp;
import com.linecorp.armeria.client.Clients;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bistros.hauto.server.api.v1.grpc.binder.GrpcTypeConverter.toTimestampProto;

public class ClientNetwork implements Runnable {

    private final PutServiceGrpc.PutServiceBlockingStub grpcBlockingService;
    private final ClientNetworkQueue queue;
    private final String carId;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /*
    Queue의 데이터를 적당하게 꺼내서 , 실제 RPC call 을 합니다.
 * 1. 데이터 유무에 관계 없이 네트워크 상태가 문제가 있다면 전송 Puase
 * 2. Queue 에 데이터가 많고 네트워크가 정상적이라면  데이터를 많이 보내게 셋팅
 * 3. Queue 에 데이터가 상관없이 네트워크 속도가 느리다면, 데이터를 적당히 보냄 (Queue 적재량보다 전송량이 작을 수 있음)
 */
    private int networkDelayTime = 1;
    private int dataSize = 10;
    private boolean active = true;


    public ClientNetwork(Simulrator simulrator) {
        carId = simulrator.getId();
        queue = new ClientNetworkQueue(3600);
        grpcBlockingService = Clients.newClient("gproto+http://127.0.0.1:8081/", PutServiceGrpc.PutServiceBlockingStub.class);
    }

    /*
        위치 정보를 큐에 저장한다
     */
    public void store(Position position) {
        queue.add(position);
    }


    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(networkDelayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Position> data = queue.getPositions(dataSize);
            if (data.size() > 0 && active) {
                call(data);
            }
        }
    }

    /*
        @TODO
          call method 를  호출하는 시점에 queue 에서 데이터가 제거된다.
          그렇기 때문에 에러가 나면 다시 한번 retry를 하는 정책이 필요하다.
     */
    public void call(List<Position> data) {
        logger.warn("{} 가 {} 개 데이터 전송 요청", carId, data.size());
        try {
            for (Position position : data) {
                CarPositionWriteRequestProto request = CarPositionWriteRequestProto.newBuilder()
                        .setId(carId)
                        .setPosition(PositionLogProto.newBuilder()
                                .setLatitude(position.getLatitude())
                                .setLongitude(position.getLongitude())
                                .setEventTime(toTimestampProto(position.getEventTime()))
                                .build())
                        .build();

                Timestamp ts = request.getPosition().getEventTime();
                grpcBlockingService.put(request).getResult();
            }
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.UNIMPLEMENTED) {
                logger.error("서버측의 API 가 규약에 어긋납니다. 네트워크 전송은 의미가 없으므로 중단하겠습니다");
                active = false; // 전송 기능을 disabled 한다.
            } else {
                logger.warn("데이터 전송에 문제가 있어서 전송 속도와 전송 데이터량을 감소시킵니다");
                changedNetowkrDelay(5);
                reduceDataSize(1);
            }
            // TODO 보내지 못하거나 실패한 Position 정보는 requeue를 하거나, retry 정책을 수립해야한다
        }
    }

    public void changedNetowkrDelay(int delay) {
        this.networkDelayTime = delay;
    }

    public void reduceDataSize(int size) {
        dataSize = size;
    }
}
