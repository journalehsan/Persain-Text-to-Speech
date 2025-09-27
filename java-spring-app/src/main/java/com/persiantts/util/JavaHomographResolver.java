package com.persiantts.util;

import java.util.List;
import java.util.Map;

public class JavaHomographResolver {
    
    private static final Map<String, Map<String, String>> HOMOGRAPH_RULES = Map.of(
        "حسن", Map.of(
            "patterns", "گفت,آمد,کرد",
            "pronunciation", "Hassan",
            "default", "Hossn"
        ),
        "خوان", Map.of(
            "patterns", "خواندن",
            "pronunciation", "khaandan",
            "default", "khaan"
        ),
        "شیر", Map.of(
            "patterns", "شیرین,شیرازی",
            "pronunciation", "shir",
            "default", "sher"
        )
    );
    
    public static String resolve(String word, List<String> contextWords) {
        if (HOMOGRAPH_RULES.containsKey(word)) {
            Map<String, String> rules = HOMOGRAPH_RULES.get(word);
            String patterns = rules.get("patterns");
            String[] patternArray = patterns.split(",");
            
            for (String pattern : patternArray) {
                if (contextWords.contains(pattern)) {
                    return rules.get("pronunciation");
                }
            }
            return rules.get("default");
        }
        
        // Basic romanization fallback
        return basicRomanization(word);
    }
    
    private static String basicRomanization(String word) {
        Map<Character, String> mapping = new java.util.HashMap<>();
        mapping.put('ا', "a");
        mapping.put('ب', "b");
        mapping.put('پ', "p");
        mapping.put('ت', "t");
        mapping.put('ث', "s");
        mapping.put('ج', "j");
        mapping.put('چ', "ch");
        mapping.put('ح', "h");
        mapping.put('خ', "kh");
        mapping.put('د', "d");
        mapping.put('ذ', "z");
        mapping.put('ر', "r");
        mapping.put('ز', "z");
        mapping.put('ژ', "zh");
        mapping.put('س', "s");
        mapping.put('ش', "sh");
        mapping.put('ص', "s");
        mapping.put('ض', "z");
        mapping.put('ط', "t");
        mapping.put('ظ', "z");
        mapping.put('ع', "a");
        mapping.put('غ', "gh");
        mapping.put('ف', "f");
        mapping.put('ق', "gh");
        mapping.put('ك', "k");
        mapping.put('ک', "k");
        mapping.put('گ', "g");
        mapping.put('ل', "l");
        mapping.put('م', "m");
        mapping.put('ن', "n");
        mapping.put('و', "v");
        mapping.put('ه', "h");
        mapping.put('ی', "y");
        mapping.put('ئ', "e");
        
        StringBuilder result = new StringBuilder();
        for (char c : word.toCharArray()) {
            result.append(mapping.getOrDefault(c, String.valueOf(c)));
        }
        return result.toString();
    }
}
