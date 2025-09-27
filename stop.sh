#!/bin/bash

# Persian TTS System - Stop Script
# This script stops all services and cleans up

set -e

echo "🛑 Stopping Persian TTS System..."
echo "================================="

# Stop and remove containers
echo "🔄 Stopping containers..."
docker-compose down

# Optional: Remove volumes (uncomment if you want to clean data)
# echo "🗑️  Removing volumes..."
# docker-compose down -v

# Optional: Remove images (uncomment if you want to clean images)
# echo "🗑️  Removing images..."
# docker-compose down --rmi all

echo ""
echo "✅ Persian TTS System stopped successfully!"
echo "================================="
echo ""

# Show remaining containers (should be empty)
echo "📦 Remaining containers:"
docker-compose ps
