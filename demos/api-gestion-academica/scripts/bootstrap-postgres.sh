#!/usr/bin/env bash
set -euo pipefail

# Admin connection (superuser). You can override via environment variables.
PG_ADMIN_HOST="${PG_ADMIN_HOST:-localhost}"
PG_ADMIN_PORT="${PG_ADMIN_PORT:-5432}"
PG_ADMIN_DB="${PG_ADMIN_DB:-postgres}"
PG_ADMIN_USER="${PG_ADMIN_USER:-postgres}"
PG_ADMIN_PASSWORD="${PG_ADMIN_PASSWORD:-}"

# Application database settings (aligned with application.yml defaults).
APP_DB_NAME="${DB_NAME:-db_academica}"
APP_DB_USER="${DB_USERNAME:-postgres}"
APP_DB_PASSWORD="${DB_PASSWORD:-postgres}"

export PGPASSWORD="${PG_ADMIN_PASSWORD}"

PSQL=(psql -h "${PG_ADMIN_HOST}" -p "${PG_ADMIN_PORT}" -U "${PG_ADMIN_USER}" -d "${PG_ADMIN_DB}" -v ON_ERROR_STOP=1)

echo "[bootstrap] Verificando/creando rol ${APP_DB_USER}..."
"${PSQL[@]}" <<SQL
DO
\$\$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '${APP_DB_USER}') THEN
    EXECUTE format('CREATE ROLE %I LOGIN PASSWORD %L', '${APP_DB_USER}', '${APP_DB_PASSWORD}');
  END IF;
END
\$\$;
SQL

echo "[bootstrap] Verificando/creando base ${APP_DB_NAME}..."
DB_EXISTS=$("${PSQL[@]}" -tAc "SELECT 1 FROM pg_database WHERE datname='${APP_DB_NAME}'")
if [[ "${DB_EXISTS}" != "1" ]]; then
  "${PSQL[@]}" -c "CREATE DATABASE ${APP_DB_NAME} OWNER ${APP_DB_USER};"
fi

echo "[bootstrap] Asegurando privilegios en ${APP_DB_NAME} para ${APP_DB_USER}..."
"${PSQL[@]}" -c "GRANT ALL PRIVILEGES ON DATABASE ${APP_DB_NAME} TO ${APP_DB_USER};"

echo "[bootstrap] OK: base ${APP_DB_NAME} lista."
