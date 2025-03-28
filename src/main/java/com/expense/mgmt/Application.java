package com.expense.mgmt;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@EnableRetry
@EnableAsync
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        log.info("Application starting.....");
        SpringApplication.run(Application.class, args);
    }

}
