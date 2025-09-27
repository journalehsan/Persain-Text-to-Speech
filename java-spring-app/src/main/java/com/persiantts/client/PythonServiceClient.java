package com.persiantts.client;

import com.persiantts.model.*;
import com.persiantts.util.JavaHomographResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class PythonServiceClient {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public PythonServiceClient(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }
    
    public TextProcessingResponse processText(String persianText) {
        try {
            TextProcessingRequest request = new TextProcessingRequest(persianText);
            
            return webClient.post()
                    .uri("/api/v1/process-text")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.isError(), response -> 
                        Mono.error(new RuntimeException("Python service error: " + response.statusCode()))
                    )
                    .bodyToMono(TextProcessingResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
                    
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Python service communication error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error calling Python service: " + e.getMessage(), e);
        }
    }
    
    public HomographResolutionResponse resolveHomograph(String word, List<String> contextWords) {
        try {
            HomographResolutionRequest request = new HomographResolutionRequest(word, contextWords, true);
            
            return webClient.post()
                    .uri("/api/v1/resolve-homograph")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(HomographResolutionResponse.class)
                    .timeout(Duration.ofSeconds(3))
                    .block();
                    
        } catch (WebClientResponseException e) {
            // Fallback to Java-based resolution
            return fallbackHomographResolution(word, contextWords);
        }
    }
    
    public HealthCheckResponse healthCheck() {
        try {
            return webClient.get()
                    .uri("/health")
                    .retrieve()
                    .bodyToMono(HealthCheckResponse.class)
                    .timeout(Duration.ofSeconds(2))
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Python service health check failed: " + e.getMessage(), e);
        }
    }
    
    private HomographResolutionResponse fallbackHomographResolution(String word, List<String> contextWords) {
        // Java-based fallback homograph resolution
        String pronunciation = JavaHomographResolver.resolve(word, contextWords);
        return new HomographResolutionResponse(word, pronunciation, 0.8);
    }
}
