package com.persiantts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomographResolutionResponse {
    
    @JsonProperty("word")
    private String word;
    
    @JsonProperty("pronunciation")
    private String pronunciation;
    
    @JsonProperty("confidence")
    private double confidence;
    
    public HomographResolutionResponse() {}
    
    public HomographResolutionResponse(String word, String pronunciation, double confidence) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.confidence = confidence;
    }
    
    // Getters and setters
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
    
    public String getPronunciation() { return pronunciation; }
    public void setPronunciation(String pronunciation) { this.pronunciation = pronunciation; }
    
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
}
