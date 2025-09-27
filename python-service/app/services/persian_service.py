import hazm
from parsivar import FindStems, Normalizer as ParsivarNormalizer
from typing import Dict, List, Any
import joblib
import numpy as np

class PersianService:
    def __init__(self):
        # Initialize Persian NLP libraries
        self.hazm_normalizer = hazm.Normalizer()
        self.parsivar_normalizer = ParsivarNormalizer()
        self.tokenizer = hazm.word_tokenize
        self.stemmer = FindStems()
        
        # Load ML models
        try:
            self.homograph_model = joblib.load('ml-models/homograph_model.pkl')
            self.vectorizer = joblib.load('ml-models/vectorizer.pkl')
        except FileNotFoundError:
            print("⚠️  ML models not found, using rule-based fallback")
            self.homograph_model = None
            self.vectorizer = None
    
    def process_persian_text(self, text: str, options: Dict[str, Any] = None) -> Dict[str, Any]:
        """Advanced Persian text processing"""
        if options is None:
            options = {}
        
        # Normalization
        normalized = self.hazm_normalizer.normalize(text)
        
        # Tokenization
        tokens = self.tokenizer(normalized)
        
        # Advanced processing based on options
        processed_text = normalized
        if options.get('remove_diacritics', True):
            processed_text = self.remove_diacritics(processed_text)
        
        if options.get('stemming', False):
            tokens = [self.stemmer.convert_to_stem(token) for token in tokens]
        
        return {
            'processed': processed_text,
            'tokens': tokens,
            'normalized': normalized
        }
    
    def resolve_homograph(self, word: str, context_words: List[str], use_ml: bool = True) -> str:
        """Resolve homograph pronunciation using ML or rules"""
        
        if use_ml and self.homograph_model is not None:
            # Use ML model for prediction
            return self._ml_homograph_resolution(word, context_words)
        else:
            # Rule-based fallback
            return self._rule_based_resolution(word, context_words)
    
    def _ml_homograph_resolution(self, word: str, context_words: List[str]) -> str:
        """Machine learning based homograph resolution"""
        features = self._extract_features(word, context_words)
        
        if self.vectorizer:
            features_vector = self.vectorizer.transform([features])
            prediction = self.homograph_model.predict(features_vector)[0]
            return prediction
        else:
            return self._rule_based_resolution(word, context_words)
    
    def _rule_based_resolution(self, word: str, context_words: List[str]) -> str:
        """Rule-based homograph resolution"""
        homograph_rules = {
            'حسن': {
                'patterns': [('گفت', 'آمد', 'کرد'), 'Hassan'],
                'default': 'Hossn'
            },
            'خوان': {
                'patterns': [('خواندن',), 'khaandan'],
                'default': 'khaan'
            },
            'شیر': {
                'patterns': [('شیرین', 'شیرازی'), 'shir'],
                'default': 'sher'
            }
        }
        
        if word in homograph_rules:
            rules = homograph_rules[word]
            for pattern, pronunciation in rules['patterns']:
                if any(context in context_words for context in pattern):
                    return pronunciation
            return rules['default']
        
        # Default romanization
        return self._basic_romanization(word)
    
    def _extract_features(self, word: str, context_words: List[str]) -> Dict[str, Any]:
        """Extract features for ML model"""
        return {
            'word': word,
            'word_length': len(word),
            'context_length': len(context_words),
            'previous_words': context_words[:2],
            'next_words': context_words[-2:],
            'has_verb_context': any(self._is_verb(w) for w in context_words)
        }
    
    def _is_verb(self, word: str) -> bool:
        """Simple verb detection (placeholder)"""
        verb_indicators = ['کرد', 'شد', 'گفت', 'آمد']
        return any(indicator in word for indicator in verb_indicators)
    
    def _basic_romanization(self, word: str) -> str:
        """Basic romanization fallback"""
        mapping = {
            'ا': 'a', 'ب': 'b', 'پ': 'p', 'ت': 't', 'ث': 's',
            'ج': 'j', 'چ': 'ch', 'ح': 'h', 'خ': 'kh', 'د': 'd',
            'ذ': 'z', 'ر': 'r', 'ز': 'z', 'ژ': 'zh', 'س': 's',
            'ش': 'sh', 'ص': 's', 'ض': 'z', 'ط': 't', 'ظ': 'z',
            'ع': 'a', 'غ': 'gh', 'ف': 'f', 'ق': 'gh', 'ك': 'k',
            'ک': 'k', 'گ': 'g', 'ل': 'l', 'م': 'm', 'ن': 'n',
            'و': 'v', 'ه': 'h', 'ی': 'y', 'ئ': 'e'
        }
        return ''.join(mapping.get(char, char) for char in word)
    
    def remove_diacritics(self, text: str) -> str:
        """Remove Persian diacritics"""
        diacritics = ['َ', 'ُ', 'ِ', 'ّ', 'ً', 'ٌ', 'ٍ']
        for diacritic in diacritics:
            text = text.replace(diacritic, '')
        return text
    
    def batch_process(self, texts: List[str], options: Dict[str, Any] = None) -> List[Dict[str, Any]]:
        """Batch process multiple texts"""
        return [self.process_persian_text(text, options) for text in texts]
