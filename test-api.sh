#!/bin/bash

# Persian TTS System - API Test Script
# This script tests the API endpoints

set -e

echo "🧪 Testing Persian TTS API..."
echo "============================="

API_BASE="http://localhost:8080/api"
PYTHON_API="http://localhost:8000"

# Test data
TEST_TEXT="سلام دنیا! این یک تست است."

echo "📝 Test text: $TEST_TEXT"
echo ""

# Test Java API Health
echo "🏥 Testing Java API health..."
if curl -s -f "$API_BASE/tts/health" > /dev/null; then
    echo "✅ Java API is healthy"
    curl -s "$API_BASE/tts/health" | jq '.' 2>/dev/null || echo "Response received (jq not available)"
else
    echo "❌ Java API health check failed"
fi
echo ""

# Test Python API Health
echo "🐍 Testing Python API health..."
if curl -s -f "$PYTHON_API/health" > /dev/null; then
    echo "✅ Python API is healthy"
    curl -s "$PYTHON_API/health" | jq '.' 2>/dev/null || echo "Response received (jq not available)"
else
    echo "❌ Python API health check failed"
fi
echo ""

# Test TTS Processing
echo "🎯 Testing TTS processing..."
RESPONSE=$(curl -s -X POST "$API_BASE/tts/process" \
    -H "Content-Type: application/json" \
    -d "{\"text\": \"$TEST_TEXT\"}")

if [ $? -eq 0 ]; then
    echo "✅ TTS processing successful"
    echo "Response:"
    echo "$RESPONSE" | jq '.' 2>/dev/null || echo "$RESPONSE"
else
    echo "❌ TTS processing failed"
fi
echo ""

# Test Cache Stats
echo "📊 Testing cache stats..."
if curl -s -f "$API_BASE/tts/cache-stats" > /dev/null; then
    echo "✅ Cache stats retrieved"
    curl -s "$API_BASE/tts/cache-stats" | jq '.' 2>/dev/null || echo "Response received (jq not available)"
else
    echo "❌ Cache stats failed"
fi
echo ""

# Test Python Text Processing
echo "🔤 Testing Python text processing..."
PYTHON_RESPONSE=$(curl -s -X POST "$PYTHON_API/api/v1/process-text" \
    -H "Content-Type: application/json" \
    -d "{\"text\": \"$TEST_TEXT\", \"options\": {}}")

if [ $? -eq 0 ]; then
    echo "✅ Python text processing successful"
    echo "Response:"
    echo "$PYTHON_RESPONSE" | jq '.' 2>/dev/null || echo "$PYTHON_RESPONSE"
else
    echo "❌ Python text processing failed"
fi
echo ""

echo "🎉 API testing completed!"
echo "============================="
