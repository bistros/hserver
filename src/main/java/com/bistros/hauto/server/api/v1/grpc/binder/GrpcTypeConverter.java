package com.bistros.hauto.server.api.v1.grpc.binder;

import com.bistros.hauto.apiv1.PositionLogProto;
import com.bistros.hauto.server.domain.model.Position;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class GrpcTypeConverter {
    private final static ZoneOffset ASIA_SEOUL = ZoneOffset.of("+9");

    public static LocalDateTime toLocalDateTime(Timestamp ts) {

        return LocalDateTime.ofEpochSecond(ts.getSeconds(), ts.getNanos(), ASIA_SEOUL);
    }

    public static Timestamp toTimestampProto(LocalDateTime ldt) {
        Instant instant = ldt.toInstant(ASIA_SEOUL);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Position toPosition(PositionLogProto proto) {
        return new Position(proto.getLatitude(), proto.getLongitude(), toLocalDateTime(proto.getEventTime()));
    }

    public static List<Position> toPosition(List<PositionLogProto> positionLogProtoList) {
        return positionLogProtoList.stream()
                .map(GrpcTypeConverter::toPosition)
                .collect(Collectors.toList());
    }

    public static PositionLogProto toPositionProto(Position position) {
        if(position == null ) {
            return PositionLogProto.getDefaultInstance();
        }
        return PositionLogProto.newBuilder()
                .setLatitude(position.getLatitude())
                .setLongitude(position.getLongitude())
                .setEventTime(toTimestampProto(position.getEventTime()))
                .build();
    }

    public static List<PositionLogProto> toPositionProto(List<Position> positionList) {
        return positionList.stream()
                .map(GrpcTypeConverter::toPositionProto)
                .collect(Collectors.toList());
    }


}
