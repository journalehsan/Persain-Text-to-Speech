from pydantic import BaseModel, Field
from typing import List, Optional, Dict, Any

class TextProcessingRequest(BaseModel):
    text: str = Field(..., description="Persian text to process")
    options: Optional[Dict[str, Any]] = Field(
        default={},
        description="Processing options"
    )

class HomographResolutionRequest(BaseModel):
    word: str = Field(..., description="Word to resolve")
    context_words: List[str] = Field(
        default=[],
        description="Context words for resolution"
    )
    use_ml: bool = Field(
        default=True,
        description="Use machine learning for resolution"
    )

class BatchProcessingRequest(BaseModel):
    texts: List[str] = Field(..., description="List of texts to process")
    options: Optional[Dict[str, Any]] = Field(default={})
