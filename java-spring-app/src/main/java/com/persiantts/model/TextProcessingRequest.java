package com.persiantts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class TextProcessingRequest {
    
    @JsonProperty("text")
    private String text;
    
    @JsonProperty("options")
    private Map<String, Object> options;
    
    public TextProcessingRequest() {}
    
    public TextProcessingRequest(String text) {
        this.text = text;
        this.options = Map.of("remove_diacritics", true);
    }
    
    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
}
