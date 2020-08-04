package com.grasshouse.dorandoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DoranDoranApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoranDoranApplication.class, args);
    }

}
