package com.bistros.hauto.server.operation.testdata;

import com.bistros.hauto.server.domain.model.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.google.common.collect.Streams.concat;

/**
 * Server 프로그램을 실행할 때 `--loadtest` 옵션이 존재하면 테스트 데이터를 저장하고 startup 합니다.
 */
@Component
@Slf4j
public class LoadTestDataRunner implements ApplicationRunner {

    private final PositionRepository repository;

    public LoadTestDataRunner(PositionRepository repository) {
        this.repository = repository;
    }

    private static final String OPTION_LOAD_TEST_DATA = "skipdata";

    @Override
    public void run(ApplicationArguments args) {
        if (args.containsOption(OPTION_LOAD_TEST_DATA)) {
            log.error("테스트 데이터를 올리지 않고 Server Application 을 실행합니다");
        } else {
            loadtestdata();
        }
    }

    private void loadtestdata() {
        InitlizedTestDataSet h = new InitlizedTestDataSet();
        concat(h.car1().stream(), h.car2().stream(), h.car3().stream())
                .forEach(p -> repository.put(p.getKey(), p.getValue()));

    }
}
