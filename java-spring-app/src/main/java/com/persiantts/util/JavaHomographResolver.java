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
        Map<Character, String> mapping = Map.of(
            'ا', "a", 'ب', "b", 'پ', "p", 'ت', "t", 'ث', "s",
            'ج', "j", 'چ', "ch", 'ح', "h", 'خ', "kh", 'د', "d",
            'ذ', "z", 'ر', "r", 'ز', "z", 'ژ', "zh", 'س', "s",
            'ش', "sh", 'ص', "s", 'ض', "z", 'ط', "t", 'ظ', "z",
            'ع', "a", 'غ', "gh", 'ف', "f", 'ق', "gh", 'ك', "k",
            'ک', "k", 'گ', "g", 'ل', "l", 'م', "m", 'ن', "n",
            'و', "v", 'ه', "h", 'ی', "y", 'ئ', "e"
        );
        
        StringBuilder result = new StringBuilder();
        for (char c : word.toCharArray()) {
            result.append(mapping.getOrDefault(c, String.valueOf(c)));
        }
        return result.toString();
    }
}
