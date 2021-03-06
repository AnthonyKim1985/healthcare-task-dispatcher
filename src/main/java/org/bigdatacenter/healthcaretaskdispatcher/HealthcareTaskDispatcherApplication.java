package org.bigdatacenter.healthcaretaskdispatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class HealthcareTaskDispatcherApplication {
    @Value("${scheduler.thread-pool-size}")
    private int threadPoolSize;

    public static void main(String[] args) {
        SpringApplication.run(HealthcareTaskDispatcherApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadPoolSize);
        return taskScheduler;
    }
}