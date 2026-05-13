#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
APP_IMAGE="${APP_IMAGE:-api-gestion-academica:security-scan}"
TRIVY_IMAGE="${TRIVY_IMAGE:-aquasec/trivy:0.71.0}"
CVSS_THRESHOLD="${CVSS_THRESHOLD:-7}"

cd "${PROJECT_ROOT}"

if ! command -v docker >/dev/null 2>&1; then
  echo "[scan] docker no esta disponible" >&2
  exit 1
fi

if ! command -v ./mvnw >/dev/null 2>&1; then
  echo "[scan] mvnw no esta disponible en el proyecto" >&2
  exit 1
fi

echo "[scan] Analizando dependencias Maven..."
./mvnw -DskipTests org.owasp:dependency-check-maven:12.2.2:check \
  -Ddependency-check.failBuildOnCVSS="${CVSS_THRESHOLD}"

echo "[scan] Construyendo imagen ${APP_IMAGE}..."
docker build -t "${APP_IMAGE}" .

echo "[scan] Analizando imagen con Trivy..."
docker run --rm \
  -v /var/run/docker.sock:/var/run/docker.sock \
  "${TRIVY_IMAGE}" image \
  --severity HIGH,CRITICAL \
  --no-progress \
  "${APP_IMAGE}"

echo "[scan] OK: analisis completado."
