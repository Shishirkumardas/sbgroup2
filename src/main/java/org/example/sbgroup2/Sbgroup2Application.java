package org.example.sbgroup2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.example.sbgroup2")
@EnableJpaRepositories(basePackages = "org.example.sbgroup2")
public class Sbgroup2Application {

    public static void main(String[] args) {
        SpringApplication.run(Sbgroup2Application.class, args);
    }

}
