package com.bistros.hauto.server.configuration;

import com.bistros.hauto.server.Infra.persistence.FilePositionRepository;
import com.bistros.hauto.server.Infra.persistence.InMemoryPositionRepository;
import com.bistros.hauto.server.domain.model.PositionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfiguration {
    @Bean
    public PositionRepository positionRepository() {
        PositionRepository inMemory = new InMemoryPositionRepository();
        return new FilePositionRepository(inMemory);
    }
}
