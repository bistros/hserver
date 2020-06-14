package com.bistros.hauto.server.api.v1.grpc.binder;

import com.google.protobuf.Timestamp;
import org.junit.Test;

import java.time.LocalDateTime;

public class GrpcTypeConverterTest {

    @Test
    public void ldtToTimeStampTest () {
        LocalDateTime ldt = LocalDateTime.of(2020,6,10,10,20,00);

        Timestamp ts = GrpcTypeConverter.toTimestampProto(ldt);
        LocalDateTime ldt2 = GrpcTypeConverter.toLocalDateTime(ts);

        System.out.println(ldt);
        System.out.println(ts.getSeconds());
        System.out.println(ldt2);
    }

}