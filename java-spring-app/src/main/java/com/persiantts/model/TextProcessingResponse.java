package com.persiantts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class TextProcessingResponse {
    
    @JsonProperty("original_text")
    private String originalText;
    
    @JsonProperty("processed_text")
    private String processedText;
    
    @JsonProperty("tokens")
    private List<String> tokens;
    
    @JsonProperty("normalized")
    private String normalized;
    
    @JsonProperty("metadata")
    private Map<String, Object> metadata;
    
    // Constructors
    public TextProcessingResponse() {}
    
    public TextProcessingResponse(String originalText, String processedText, 
                                List<String> tokens, String normalized, 
                                Map<String, Object> metadata) {
        this.originalText = originalText;
        this.processedText = processedText;
        this.tokens = tokens;
        this.normalized = normalized;
        this.metadata = metadata;
    }
    
    // Getters and setters
    public String getOriginalText() { return originalText; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }
    
    public String getProcessedText() { return processedText; }
    public void setProcessedText(String processedText) { this.processedText = processedText; }
    
    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }
    
    public String getNormalized() { return normalized; }
    public void setNormalized(String normalized) { this.normalized = normalized; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
