#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_ROOT"

CONTAINER_NAME="binastar-starter-postgres"
DB_NAME="binastar-starter"
DB_USER="binastar-starter"

if ! command -v docker >/dev/null 2>&1; then
	echo "Fehlt: docker"
	exit 1
fi

if ! docker info >/dev/null 2>&1; then
	echo "Docker läuft nicht. Starte Docker Desktop, Colima oder den Docker-Daemon und versuche es erneut."
	exit 1
fi

if ! docker exec "$CONTAINER_NAME" pg_isready -U "$DB_USER" -d "$DB_NAME" >/dev/null 2>&1; then
	echo "PostgreSQL ist nicht bereit."
	echo "Starte zuerst die Datenbank mit: docker compose up -d"
	echo "Warte danach, bis docker compose ps den Status healthy zeigt."
	exit 1
fi

echo "Importiere Workshop-Ablauf..."
docker exec -i "$CONTAINER_NAME" psql -v ON_ERROR_STOP=1 -U "$DB_USER" -d "$DB_NAME" <<'SQL'
CREATE TABLE IF NOT EXISTS timetable (
    id       SERIAL PRIMARY KEY,
    tag_nr   INT  NOT NULL,
    tag      TEXT NOT NULL,
    block_nr INT  NOT NULL,
    block    TEXT NOT NULL,
    inhalt   TEXT NOT NULL
);

TRUNCATE TABLE timetable RESTART IDENTITY;

INSERT INTO timetable (tag_nr, tag, block_nr, block, inhalt) VALUES
    (1, 'Dienstag 30. Juni', 1, 'Vormittag I', 'Kickoff & KI-Grundlagen'),
    (2, 'Mittwoch 1. Juli', 1, 'Vormittag I', 'Meetings & Kanban-Einführung'),
    (3, 'Donnerstag 2. Juli', 1, 'Vormittag I', 'SDD Theorie I – Was ist SDD?'),
    (4, 'Freitag 3. Juli', 1, 'Vormittag I', 'Rückblick + Champions + Pilot-Sprint'),
    (1, 'Dienstag 30. Juni', 2, 'Vormittag II', 'KI im Alltag, Datenschutz & IP'),
    (2, 'Mittwoch 1. Juli', 2, 'Vormittag II', 'Erste Retrospektive'),
    (3, 'Donnerstag 2. Juli', 2, 'Vormittag II', 'SDD Theorie II – Gute Specs'),
    (4, 'Freitag 3. Juli', 2, 'Vormittag II', 'Pilot-Sprint + Struktur Folgewochen'),
    (1, 'Dienstag 30. Juni', 3, 'Nachmittag I', 'Live-Demo: Agentische Entwicklung'),
    (2, 'Mittwoch 1. Juli', 3, 'Nachmittag I', 'Tickets – Theorie'),
    (3, 'Donnerstag 2. Juli', 3, 'Nachmittag I', 'SDD Live-Demo + Spec schreiben'),
    (4, 'Freitag 3. Juli', 3, 'Nachmittag I', 'Tech-Ausblick & Token-Effizienz'),
    (1, 'Dienstag 30. Juni', 4, 'Nachmittag II', 'Skills – Agents & Guardrails'),
    (2, 'Mittwoch 1. Juli', 4, 'Nachmittag II', 'Tickets – Praxis & Kanban-Board'),
    (3, 'Donnerstag 2. Juli', 4, 'Nachmittag II', 'SDD auf echtem Ticket'),
    (4, 'Freitag 3. Juli', 4, 'Nachmittag II', 'Diskussionsrunde');
SQL

echo
echo "Kontrolle: erster Workshop-Tag"
docker exec "$CONTAINER_NAME" psql -U "$DB_USER" -d "$DB_NAME" \
	-c "SELECT block, inhalt FROM timetable WHERE tag_nr = 1 ORDER BY block_nr;"

echo
echo "Testdaten importiert."
