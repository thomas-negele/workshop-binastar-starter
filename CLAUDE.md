# Workshop CSV Import – Projektkontext

## Stack
- Java 17, kein Framework – plain Java
- PostgreSQL 15 via Docker Compose
- Build: `javac`, kein Maven/Gradle

## Datenbank
- Host: localhost:5432
- DB: workshop
- User: workshop
- Password: workshop
- JDBC-Treiber liegt unter libs/postgresql-42.7.4.jar

## Dateien
- Importdatei: customers.csv (Projektroot)
- Schema bereits angelegt via Docker init (Tabelle customers mit Unique-Constraint auf firstname+lastname)

## Konventionen
- Fehler-Log: duplicate_log.csv im Projektroot
- Kein Abbruch bei Einzelfehlern – Import läuft durch
