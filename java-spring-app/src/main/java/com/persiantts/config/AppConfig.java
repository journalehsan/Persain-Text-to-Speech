package com.persiantts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Value("${app.python-service.url}")
    private String pythonServiceUrl;
    
    @Value("${app.python-service.timeout}")
    private int pythonServiceTimeout;
    
    public String getPythonServiceUrl() {
        return pythonServiceUrl;
    }
    
    public int getPythonServiceTimeout() {
        return pythonServiceTimeout;
    }
}
