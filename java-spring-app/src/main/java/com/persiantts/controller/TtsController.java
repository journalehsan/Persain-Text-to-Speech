package com.persiantts.controller;

import com.persiantts.model.*;
import com.persiantts.service.PersianTtsService;
import com.persiantts.service.FastPersianRomanizer;
import com.persiantts.client.PythonServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tts")
@CrossOrigin(origins = {"http://localhost:4433", "http://localhost:3000"})
public class TtsController {
    
    private final PersianTtsService ttsService;
    private final FastPersianRomanizer romanizer;
    private final PythonServiceClient pythonClient;
    
    @Autowired
    public TtsController(PersianTtsService ttsService, 
                        FastPersianRomanizer romanizer,
                        PythonServiceClient pythonClient) {
        this.ttsService = ttsService;
        this.romanizer = romanizer;
        this.pythonClient = pythonClient;
    }
    
    @PostMapping("/process")
    public ResponseEntity<TtsResult> processText(@RequestBody Map<String, String> request) {
        String persianText = request.get("text");
        if (persianText == null || persianText.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        TtsResult result = ttsService.processTextForTts(persianText);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        try {
            HealthCheckResponse pythonHealth = pythonClient.healthCheck();
            return ResponseEntity.ok(Map.of(
                "java_service", "healthy",
                "python_service", pythonHealth.getStatus(),
                "cache_size", romanizer.getCacheSize()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "java_service", "healthy",
                "python_service", "unhealthy",
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/cache-stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        return ResponseEntity.ok(Map.of(
            "cache_size", romanizer.getCacheSize()
        ));
    }
    
    @PostMapping("/clear-cache")
    public ResponseEntity<Map<String, String>> clearCache() {
        romanizer.clearCache();
        return ResponseEntity.ok(Map.of("message", "Cache cleared successfully"));
    }
}
