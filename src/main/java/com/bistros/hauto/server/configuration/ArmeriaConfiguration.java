package com.bistros.hauto.server.configuration;

import com.bistros.hauto.server.api.v1.http.HttpServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.armeria.server.annotation.JacksonRequestConverterFunction;
import com.linecorp.armeria.server.annotation.JacksonResponseConverterFunction;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ArmeriaConfiguration {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(
            HttpServiceImpl httpService, ObjectMapper mapper,
            List<io.grpc.BindableService> grpcServiceImplList) {

        return builder -> {
            builder.serviceUnder("/docs", new DocService());
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.common(), true);


            // HTTP Service
            builder.annotatedService(httpService,
                    new JacksonRequestConverterFunction(mapper),
                    new JacksonResponseConverterFunction(mapper));

            // Grpc Service
            GrpcServiceBuilder grpcServiceBuilder = GrpcService.builder();
            grpcServiceImplList.forEach(grpcServiceBuilder::addService);
            builder.service(grpcServiceBuilder.build());
        };
    }

}
