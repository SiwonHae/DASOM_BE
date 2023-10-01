package com.oss2.dasom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DasomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasomApplication.class, args);
    }

}
