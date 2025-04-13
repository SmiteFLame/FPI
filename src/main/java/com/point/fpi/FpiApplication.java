package com.point.fpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FpiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpiApplication.class, args);
    }

}
