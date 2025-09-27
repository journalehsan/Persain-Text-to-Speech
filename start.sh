#!/bin/bash

# Persian TTS System - Start Script
# This script starts all services using Docker Compose

set -e

echo "🚀 Starting Persian TTS System..."
echo "=================================="

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Check if Docker Compose is available
if ! docker compose version > /dev/null 2>&1; then
    echo "❌ Docker Compose is not available. Please ensure Docker Compose V2 is installed."
    exit 1
fi

# Create necessary directories
echo "📁 Creating necessary directories..."
mkdir -p python-service/ml-models
mkdir -p nginx/logs

# Build and start services
echo "🔨 Building and starting services..."
docker compose up --build -d

# Wait for services to be healthy
echo "⏳ Waiting for services to be ready..."
sleep 10

# Check service health
echo "🏥 Checking service health..."

# Check Python service
if curl -f http://localhost:8888/health > /dev/null 2>&1; then
    echo "✅ Python service is healthy"
else
    echo "⚠️  Python service health check failed"
fi

# Check Java service
if curl -f http://localhost:8081/api/tts/health > /dev/null 2>&1; then
    echo "✅ Java service is healthy"
else
    echo "⚠️  Java service health check failed"
fi

# Check Nginx
if curl -f http://localhost:4433/health > /dev/null 2>&1; then
    echo "✅ Nginx is healthy"
else
    echo "⚠️  Nginx health check failed"
fi

echo ""
echo "🎉 Persian TTS System is starting up!"
echo "=================================="
echo "📱 Frontend: http://localhost:4433"
echo "☕ Java API: http://localhost:8081/api"
echo "🐍 Python API: http://localhost:8888"
echo ""
echo "📊 To view logs: docker compose logs -f"
echo "🛑 To stop: ./stop.sh"
echo ""

# Show running containers
echo "📦 Running containers:"
docker compose ps
