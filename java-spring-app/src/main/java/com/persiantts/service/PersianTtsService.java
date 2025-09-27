package com.persiantts.service;

import com.persiantts.client.PythonServiceClient;
import com.persiantts.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersianTtsService {
    
    private final PythonServiceClient pythonClient;
    private final FastPersianRomanizer romanizer;
    
    @Autowired
    public PersianTtsService(PythonServiceClient pythonClient, 
                           FastPersianRomanizer romanizer) {
        this.pythonClient = pythonClient;
        this.romanizer = romanizer;
    }
    
    public TtsResult processTextForTts(String persianText) {
        try {
            // Step 1: Advanced processing with Python
            TextProcessingResponse processed = pythonClient.processText(persianText);
            
            // Step 2: Romanize with high-performance Java
            String romanized = romanizer.romanizeFast(processed.getProcessedText());
            
            // Step 3: Resolve complex homographs with Python ML
            List<String> resolvedWords = resolveHomographs(processed.getTokens());
            
            return new TtsResult(
                persianText,
                romanized,
                resolvedWords,
                processed.getTokens(),
                "success"
            );
            
        } catch (Exception e) {
            // Fallback to Java-only processing
            return fallbackProcessing(persianText);
        }
    }
    
    private List<String> resolveHomographs(List<String> tokens) {
        List<String> resolved = new ArrayList<>();
        
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            
            if (isComplexHomograph(token)) {
                List<String> context = getContextWords(tokens, i, 2);
                HomographResolutionResponse resolution = 
                    pythonClient.resolveHomograph(token, context);
                resolved.add(resolution.getPronunciation());
            } else {
                resolved.add(romanizer.romanizeFast(token));
            }
        }
        
        return resolved;
    }
    
    private boolean isComplexHomograph(String word) {
        // Define words that need Python's ML resolution
        List<String> complexHomographs = List.of("حسن", "خوان", "شیر", "پر");
        return complexHomographs.contains(word);
    }
    
    private List<String> getContextWords(List<String> tokens, int index, int window) {
        int start = Math.max(0, index - window);
        int end = Math.min(tokens.size(), index + window + 1);
        
        List<String> context = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (i != index) {
                context.add(tokens.get(i));
            }
        }
        return context;
    }
    
    private TtsResult fallbackProcessing(String persianText) {
        // Java-only fallback processing
        String romanized = romanizer.romanizeFast(persianText);
        return new TtsResult(
            persianText,
            romanized,
            List.of(romanized.split(" ")),
            List.of(persianText.split(" ")),
            "fallback"
        );
    }
}
