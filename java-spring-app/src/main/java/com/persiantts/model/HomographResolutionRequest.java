package com.persiantts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class HomographResolutionRequest {
    
    @JsonProperty("word")
    private String word;
    
    @JsonProperty("context_words")
    private List<String> contextWords;
    
    @JsonProperty("use_ml")
    private boolean useMl;
    
    public HomographResolutionRequest() {}
    
    public HomographResolutionRequest(String word, List<String> contextWords, boolean useMl) {
        this.word = word;
        this.contextWords = contextWords;
        this.useMl = useMl;
    }
    
    // Getters and setters
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
    
    public List<String> getContextWords() { return contextWords; }
    public void setContextWords(List<String> contextWords) { this.contextWords = contextWords; }
    
    public boolean isUseMl() { return useMl; }
    public void setUseMl(boolean useMl) { this.useMl = useMl; }
}
