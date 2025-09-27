package com.persiantts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TtsResult {
    
    @JsonProperty("original_text")
    private String originalText;
    
    @JsonProperty("romanized_text")
    private String romanizedText;
    
    @JsonProperty("resolved_words")
    private List<String> resolvedWords;
    
    @JsonProperty("tokens")
    private List<String> tokens;
    
    @JsonProperty("status")
    private String status;
    
    public TtsResult() {}
    
    public TtsResult(String originalText, String romanizedText, 
                    List<String> resolvedWords, List<String> tokens, String status) {
        this.originalText = originalText;
        this.romanizedText = romanizedText;
        this.resolvedWords = resolvedWords;
        this.tokens = tokens;
        this.status = status;
    }
    
    // Getters and setters
    public String getOriginalText() { return originalText; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }
    
    public String getRomanizedText() { return romanizedText; }
    public void setRomanizedText(String romanizedText) { this.romanizedText = romanizedText; }
    
    public List<String> getResolvedWords() { return resolvedWords; }
    public void setResolvedWords(List<String> resolvedWords) { this.resolvedWords = resolvedWords; }
    
    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
