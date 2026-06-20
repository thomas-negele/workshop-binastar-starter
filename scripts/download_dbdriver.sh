#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_ROOT"

JDBC_VERSION="42.7.4"
JDBC_JAR="libs/postgresql-${JDBC_VERSION}.jar"
JDBC_URL="https://jdbc.postgresql.org/download/postgresql-${JDBC_VERSION}.jar"
tmp_file="${JDBC_JAR}.tmp"

cleanup() {
	rm -f "$tmp_file"
}
trap cleanup EXIT

if ! command -v curl >/dev/null 2>&1; then
	echo "Fehlt: curl"
	exit 1
fi

if [ -f "$JDBC_JAR" ]; then
	echo "PostgreSQL-JDBC-Treiber ist vorhanden: $JDBC_JAR"
	exit 0
fi

echo "Lade PostgreSQL-JDBC-Treiber..."
mkdir -p libs
rm -f "$tmp_file"
curl -fL --retry 3 --connect-timeout 15 -o "$tmp_file" "$JDBC_URL"
mv "$tmp_file" "$JDBC_JAR"
echo "Gespeichert: $JDBC_JAR"
