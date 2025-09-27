package com.persiantts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HealthCheckResponse {
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("service")
    private String service;
    
    @JsonProperty("version")
    private String version;
    
    public HealthCheckResponse() {}
    
    public HealthCheckResponse(String status, String service, String version) {
        this.status = status;
        this.service = service;
        this.version = version;
    }
    
    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}
