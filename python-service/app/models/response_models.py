from pydantic import BaseModel
from typing import List, Dict, Any

class HealthCheckResponse(BaseModel):
    status: str
    service: str
    version: str

class TextProcessingResponse(BaseModel):
    original_text: str
    processed_text: str
    tokens: List[str]
    normalized: str
    metadata: Dict[str, Any] = {}

class HomographResolutionResponse(BaseModel):
    word: str
    pronunciation: str
    confidence: float
