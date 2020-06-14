package com.bistros.hauto.client.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.client")
public class ClientProperties {
    private int time;
    private int size;
}
