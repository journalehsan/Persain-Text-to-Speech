package com.persiantts.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FastPersianRomanizer {
    
    private static final int MAX_CHAR = 0xFFFF;
    private final String[] charMap = new String[MAX_CHAR];
    private final Map<String, String> wordCache = new ConcurrentHashMap<>();
    
    public FastPersianRomanizer() {
        initializeCharacterMap();
    }
    
    private void initializeCharacterMap() {
        // Basic Persian to Latin mapping
        charMap['ا'] = "a"; charMap['ب'] = "b"; charMap['پ'] = "p";
        charMap['ت'] = "t"; charMap['ث'] = "s"; charMap['ج'] = "j";
        charMap['چ'] = "ch"; charMap['ح'] = "h"; charMap['خ'] = "kh";
        charMap['د'] = "d"; charMap['ذ'] = "z"; charMap['ر'] = "r";
        charMap['ز'] = "z"; charMap['ژ'] = "zh"; charMap['س'] = "s";
        charMap['ش'] = "sh"; charMap['ص'] = "s"; charMap['ض'] = "z";
        charMap['ط'] = "t"; charMap['ظ'] = "z"; charMap['ع'] = "a";
        charMap['غ'] = "gh"; charMap['ف'] = "f"; charMap['ق'] = "gh";
        charMap['ك'] = "k"; charMap['ک'] = "k"; charMap['گ'] = "g";
        charMap['ل'] = "l"; charMap['م'] = "m"; charMap['ن'] = "n";
        charMap['و'] = "v"; charMap['ه'] = "h"; charMap['ی'] = "y";
        charMap['ئ'] = "e";
    }
    
    public String romanizeFast(String persianText) {
        if (wordCache.containsKey(persianText)) {
            return wordCache.get(persianText);
        }
        
        StringBuilder result = new StringBuilder(persianText.length() * 2);
        
        for (char c : persianText.toCharArray()) {
            if (c < MAX_CHAR && charMap[c] != null) {
                result.append(charMap[c]);
            } else {
                result.append(c);
            }
        }
        
        String romanized = result.toString();
        wordCache.put(persianText, romanized);
        
        return romanized;
    }
    
    public void clearCache() {
        wordCache.clear();
    }
    
    public long getCacheSize() {
        return wordCache.size();
    }
}
