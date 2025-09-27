from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    HOST: str = "0.0.0.0"
    PORT: int = 8000
    DEBUG: bool = True
    PYTHON_SERVICE_URL: str = "http://localhost:8000"
    
    class Config:
        env_file = ".env"

settings = Settings()
