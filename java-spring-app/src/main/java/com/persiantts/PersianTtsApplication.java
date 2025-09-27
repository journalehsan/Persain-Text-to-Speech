package com.persiantts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PersianTtsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PersianTtsApplication.class, args);
    }
    
    @Bean
    public WebClient pythonServiceWebClient() {
        return WebClient.builder()
                .baseUrl("http://python-service:8000")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
