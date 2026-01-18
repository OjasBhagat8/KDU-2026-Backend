package com.library.libraryprod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.library.libraryprod")
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class ProductionReadyLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductionReadyLibApplication.class, args);
    }
}