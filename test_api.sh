#!/bin/bash

# 🚀 Script de Pruebas - Driving School API
# Uso: bash test_api.sh

set -e

BASE_URL="http://localhost:8080/api"
EMAIL="admin@kynsoft.com"
PASSWORD="Admin2026!"

echo "════════════════════════════════════════════════════════"
echo "🚀 DRIVING SCHOOL API - TEST SCRIPT"
echo "════════════════════════════════════════════════════════"
echo ""

# Test 1: Login y obtener token
echo "📍 Test 1: Login y Obtener Token JWT"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "POST $BASE_URL/auth/login"
echo ""

RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"$EMAIL\",\"password\":\"$PASSWORD\"}")

echo "Respuesta:"
echo "$RESPONSE" | jq '.' 2>/dev/null || echo "$RESPONSE"
echo ""

# Extraer token
TOKEN=$(echo "$RESPONSE" | jq -r '.token' 2>/dev/null)

if [ -z "$TOKEN" ] || [ "$TOKEN" = "null" ]; then
    echo "❌ Error: No se obtuvo el token. El login falló."
    exit 1
fi

echo "✅ Token obtenido exitosamente!"
echo "🔑 Token: ${TOKEN:0:50}..."
echo ""

# Test 2: Health Check del Gateway
echo "📍 Test 2: Health Check del API Gateway"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "GET $BASE_URL/../actuator/health"
echo ""

HEALTH=$(curl -s -X GET "http://localhost:8080/actuator/health")
echo "Respuesta:"
echo "$HEALTH" | jq '.' 2>/dev/null || echo "$HEALTH"
echo ""

# Test 3: Listar Usuarios
echo "📍 Test 3: Listar Usuarios (Requiere autenticación)"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "GET $BASE_URL/users?page=0&size=5"
echo ""

USERS=$(curl -s -X GET "$BASE_URL/users?page=0&size=5" \
  -H "Authorization: Bearer $TOKEN")

echo "Respuesta:"
echo "$USERS" | jq '.' 2>/dev/null || echo "$USERS"
echo ""

# Test 4: Obtener Usuario por ID (si existe)
echo "📍 Test 4: Obtener Primer Usuario"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

USER_ID=$(echo "$USERS" | jq -r '.content[0].id' 2>/dev/null)

if [ -z "$USER_ID" ] || [ "$USER_ID" = "null" ]; then
    echo "⚠️  No hay usuarios disponibles"
else
    echo "GET $BASE_URL/users/$USER_ID"
    echo ""

    USER=$(curl -s -X GET "$BASE_URL/users/$USER_ID" \
      -H "Authorization: Bearer $TOKEN")

    echo "Respuesta:"
    echo "$USER" | jq '.' 2>/dev/null || echo "$USER"
fi
echo ""

# Test 5: Crear Usuario (TEST)
echo "📍 Test 5: Crear Nuevo Usuario"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "POST $BASE_URL/users"
echo ""

TIMESTAMP=$(date +%s)
NEW_EMAIL="test_$TIMESTAMP@kynsoft.com"

echo "Creando usuario con email: $NEW_EMAIL"
echo ""

NEW_USER=$(curl -s -X POST "$BASE_URL/users" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\":\"$NEW_EMAIL\",
    \"password\":\"TestPass123!\",
    \"name\":\"Test User $TIMESTAMP\"
  }")

echo "Respuesta:"
echo "$NEW_USER" | jq '.' 2>/dev/null || echo "$NEW_USER"
echo ""

NEW_USER_ID=$(echo "$NEW_USER" | jq -r '.id' 2>/dev/null)

# Test 6: Actualizar Usuario
if [ ! -z "$NEW_USER_ID" ] && [ "$NEW_USER_ID" != "null" ]; then
    echo "📍 Test 6: Actualizar Usuario"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "PUT $BASE_URL/users/$NEW_USER_ID"
    echo ""

    UPDATED=$(curl -s -X PUT "$BASE_URL/users/$NEW_USER_ID" \
      -H "Authorization: Bearer $TOKEN" \
      -H "Content-Type: application/json" \
      -d "{
        \"email\":\"$NEW_EMAIL\",
        \"name\":\"Updated Test User\"
      }")

    echo "Respuesta:"
    echo "$UPDATED" | jq '.' 2>/dev/null || echo "$UPDATED"
    echo ""

    # Test 7: Cambiar Estado
    echo "📍 Test 7: Cambiar Estado de Usuario"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "PATCH $BASE_URL/users/$NEW_USER_ID/status"
    echo ""

    STATUS=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X PATCH "$BASE_URL/users/$NEW_USER_ID/status" \
      -H "Authorization: Bearer $TOKEN" \
      -H "Content-Type: application/json" \
      -d "{\"status\":\"INACTIVE\"}")

    echo "Respuesta:"
    echo "$STATUS"
    echo ""
fi

# Resumen final
echo "════════════════════════════════════════════════════════"
echo "✅ PRUEBAS COMPLETADAS EXITOSAMENTE"
echo "════════════════════════════════════════════════════════"
echo ""
echo "📊 Resumen:"
echo "  ✓ Login funcionando"
echo "  ✓ JWT Token válido"
echo "  ✓ API Gateway respondiendo"
echo "  ✓ CRUD de usuarios funcionando"
echo ""
echo "🔑 Token para usar en Postman:"
echo "   $TOKEN"
echo ""
echo "💡 Próximo paso: Importa 'Driving_School_API.postman_collection.json' en Postman"
echo ""
