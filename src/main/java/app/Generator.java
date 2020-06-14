package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.bistros.hauto.client")
public class Generator {
    public static void main(String[] args) {
        SpringApplication.run(Generator.class, args);
    }


}
