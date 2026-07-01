# Workshop Gruppen-Task – Projektkontext

## Stack
- Java 17, kein Framework – plain Java
- PostgreSQL 15 via Docker Compose
- Build: `javac`, kein Maven/Gradle
- JDBC-Treiber liegt unter libs/postgresql-42.7.4.jar

## Datenbank
- Host: localhost:5432
- DB: workshop
- User: workshop
- Password: workshop

## Ausgangssituation
Die Datenbank ist bereits gefüllt. Ein CSV-Import hat stattgefunden und dabei:
- 91 Datensätze importiert (in Tabelle `customers`)
- Einige Datensätze übersprungen (Duplikate, Validierungsfehler)
- Einige Felder konnten nicht geparst werden → NULL gespeichert

