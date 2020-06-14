package app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.bistros.hauto.tester")
public class Tester {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Tester.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
    }

}
