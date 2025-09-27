# Persian Text-to-Speech System

A hybrid architecture Persian TTS system combining Java Spring Boot (main application) with Python FastAPI (ML services) and a modern web frontend.

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Hybrid Persian TTS System                │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐      REST API       ┌─────────────────┐ │
│  │   Java Spring   │ ◄─────────────────► │  Python FastAPI │ │
│  │   Main App      │                     │    ML Services  │ │
│  └─────────────────┘                     └─────────────────┘ │
│         │                               │                    │
│         ▼                               ▼                    │
│  ┌───────────────┐             ┌──────────────────┐         │
│  │ High-Perf     │             │ Advanced Persian │         │
│  │ Romanization  │             │ NLP/ML Features  │         │
│  └───────────────┘             └──────────────────┘         │
└─────────────────────────────────────────────────────────────┘
```

## Services

- **Java Spring Boot** (Port 8080): Main application with high-performance romanization
- **Python FastAPI** (Port 8000): Advanced Persian NLP and ML services
- **Nginx** (Port 4433): Reverse proxy and static file serving
- **Frontend**: TailwindCSS + jQuery demo interface

## Quick Start

### Prerequisites

- Docker and Docker Compose
- Git

### Running the System

1. **Start all services:**
   ```bash
   ./start.sh
   ```

2. **Access the application:**
   - Frontend: http://localhost:4433
   - Java API: http://localhost:8080/api
   - Python API: http://localhost:8000

3. **Stop all services:**
   ```bash
   ./stop.sh
   ```

## API Endpoints

### Java Spring Boot (Main API)

- `POST /api/tts/process` - Process Persian text for TTS
- `GET /api/tts/health` - Health check with service status
- `GET /api/tts/cache-stats` - Romanization cache statistics
- `POST /api/tts/clear-cache` - Clear romanization cache

### Python FastAPI (ML Services)

- `GET /health` - Health check
- `POST /api/v1/process-text` - Advanced Persian text processing
- `POST /api/v1/resolve-homograph` - ML-based homograph resolution
- `POST /api/v1/batch-process` - Batch text processing

## Development

### Local Development (without Docker)

1. **Python Service:**
   ```bash
   cd python-service
   pip install -r requirements.txt
   uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
   ```

2. **Java Service:**
   ```bash
   cd java-spring-app
   mvn spring-boot:run
   ```

3. **Frontend:**
   ```bash
   cd frontend
   # Serve with any static file server, e.g.:
   python -m http.server 3000
   ```

### Building Individual Services

```bash
# Build Python service
docker build -t persian-tts-python ./python-service

# Build Java service
docker build -t persian-tts-java ./java-spring-app
```

## Features

- **Advanced Persian Text Processing**: Using Hazm and Parsivar libraries
- **High-Performance Romanization**: Optimized Java implementation with caching
- **ML-Based Homograph Resolution**: Context-aware pronunciation resolution
- **Modern Web Interface**: Responsive design with TailwindCSS
- **Health Monitoring**: Comprehensive health checks for all services
- **Docker Support**: Easy deployment and scaling

## Configuration

### Environment Variables

- `APP_PYTHON_SERVICE_URL`: Python service URL (default: http://python-service:8000)
- `DEBUG`: Enable debug mode (default: false)
- `HOST`: Service host (default: 0.0.0.0)

### Nginx Configuration

The nginx configuration includes:
- Reverse proxy to Java backend
- Static file serving for frontend
- CORS headers for API access
- Rate limiting for API endpoints
- Gzip compression

## Monitoring

### Health Checks

All services include health check endpoints:
- Java: `GET /api/tts/health`
- Python: `GET /health`
- Nginx: `GET /health`

### Logs

View logs for all services:
```bash
docker-compose logs -f
```

View logs for specific service:
```bash
docker-compose logs -f java-app
docker-compose logs -f python-service
docker-compose logs -f nginx
```

## Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure ports 8000, 8080, and 4433 are available
2. **Docker not running**: Start Docker daemon
3. **Service health failures**: Check logs with `docker-compose logs`

### Reset Everything

```bash
./stop.sh
docker-compose down -v --rmi all
docker system prune -f
./start.sh
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test with the provided scripts
5. Submit a pull request

## License

This project is licensed under the MIT License.
