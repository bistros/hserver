package com.bistros.hauto.client;

import com.bistros.hauto.client.configuration.ClientProperties;
import com.bistros.hauto.client.model.AutoMobileClient;
import com.bistros.hauto.client.model.Simulrator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class ClientRunner implements ApplicationListener<ApplicationReadyEvent> {

    private ClientProperties properties;

    public void init() {
        for (int i = 1; i <= properties.getSize(); i++) {
            Simulrator simulrator = new Simulrator("A" + i);
            Thread r = new Thread(new AutoMobileClient(simulrator, properties.getTime()));
            r.start();
        }
        log.warn("자동으로 위치 정보를 생성하는 스레드를 {} 개 만들었습니다. 위치 정보 {}초에 한번씩 발생합니다. ", properties.getSize(), properties.getTime());
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        init();
    }
}
