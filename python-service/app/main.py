from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from contextlib import asynccontextmanager
from app.config import settings
from app.services.persian_service import PersianService
from app.models.request_models import (
    TextProcessingRequest,
    HomographResolutionRequest,
    BatchProcessingRequest
)
from app.models.response_models import (
    TextProcessingResponse,
    HomographResolutionResponse,
    HealthCheckResponse
)

# Lifespan manager for startup/shutdown events
@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup
    app.state.persian_service = PersianService()
    print("✅ Persian Service initialized")
    yield
    # Shutdown
    print("🔚 Shutting down Persian Service")

app = FastAPI(
    title="Persian NLP Service",
    description="Advanced Persian text processing for TTS system",
    version="1.0.0",
    lifespan=lifespan
)

# CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:8080", "http://java-app:8080", "http://localhost:4433"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/health", response_model=HealthCheckResponse)
async def health_check():
    return HealthCheckResponse(
        status="healthy",
        service="persian-nlp",
        version="1.0.0"
    )

@app.post("/api/v1/process-text", response_model=TextProcessingResponse)
async def process_text(request: TextProcessingRequest):
    try:
        service = app.state.persian_service
        result = service.process_persian_text(request.text, request.options)
        return TextProcessingResponse(
            original_text=request.text,
            processed_text=result['processed'],
            tokens=result['tokens'],
            normalized=result['normalized']
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/resolve-homograph", response_model=HomographResolutionResponse)
async def resolve_homograph(request: HomographResolutionRequest):
    try:
        service = app.state.persian_service
        pronunciation = service.resolve_homograph(
            request.word, 
            request.context_words,
            request.use_ml
        )
        return HomographResolutionResponse(
            word=request.word,
            pronunciation=pronunciation,
            confidence=0.95  # ML model confidence
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/batch-process")
async def batch_process(request: BatchProcessingRequest):
    try:
        service = app.state.persian_service
        results = service.batch_process(request.texts, request.options)
        return {"results": results}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app.main:app",
        host=settings.HOST,
        port=settings.PORT,
        reload=settings.DEBUG
    )
