# workshop-binastar-starter

**Setup-Projekt für den AI-Agile Workshop.** Dieses Repository ist die
Spielwiese, mit der wir im Workshop unser komplettes Setup einmal einrichten
und testen: Eclipse, Terminal, Git, Claude Code und eine PostgreSQL-Datenbank
im Docker-Container.

Ziel ist, dass alle möglichst auf dem gleichen technischen Stand sind und wir
während des Workshops möglichst wenig Zeit mit Setup-Problemen verlieren.
Arbeite die Abschnitte der Reihe nach durch und hake am Ende die Checkliste ab.

Wenn du bisher kaum mit Git, Java oder Programmierung gearbeitet hast, musst du
nicht zwingend alle technischen Schritte allein durchführen. Im Idealfall können
alle selbst mit Claude Code, Terminal und Eclipse arbeiten. Stimm dich trotzdem
in deiner Gruppe ab, wer das lokale Setup übernimmt und bei den technischen
Aufgaben führt.

## Überblick

Das Projekt enthält eine einfache `Timetable`-Klasse, die eine Begrüßung ausgibt und
anschließend den Workshop-Ablauf laden und als Tabelle in der Konsole anzeigen
lässt.

Es dient als **Setup des Tech-Stacks** (Eclipse, Java, Git, Claude Code, Docker,
PostgreSQL) und als Startpunkt, auf dem die Workshop-Teilnehmer ihre
Gruppenaufgaben aufbauen.

Das Projekt ist absichtlich minimalistisch gehalten: kein Maven oder Gradle,
keine ausgeprägte Architektur und nur wenige Klassen. So bleibt die Komplexität
passend zum Workshop-Scope, und es wird nicht schon zu viel für die späteren
Gruppenarbeiten vorgegeben.

- **Packages:** `de.thomasnegele.workshop.binastar.starter` und
  `de.thomasnegele.workshop.binastar.starter.timetable`
- **Java-Version:** Java 11 oder neuer
- **Quellordner:** `src/`
- **Abhängigkeiten:** PostgreSQL-JDBC-Treiber (wird bei Bedarf nach `libs/` geladen)

## Projektstruktur

```
workshop-binastar-starter/
├── src/
│   └── de/thomasnegele/workshop/binastar/starter/
│       ├── Timetable.java
│       └── timetable/
│           ├── TimetablePrinter.java
│           ├── TimetableRepository.java
│           └── TimetableSlot.java
├── scripts/
│   ├── download_dbdriver.sh
│   └── insert_testdata.sh
├── libs/
│   └── postgresql-42.7.4.jar  (wird lokal heruntergeladen, nicht versioniert)
├── docker-compose.yml
├── README.md
└── .classpath / .project
```

## Erste Schritte: Projekt auschecken und Setup prüfen

Diese Schritte richten das Projekt und die wichtigsten Werkzeuge ein. Am besten
der Reihe nach durcharbeiten. Danach folgen Datenbank und Java-Programm als
eigene Abschnitte.

### 1. Git prüfen oder installieren

Im Terminal prüfen:

```bash
git --version
```

Falls Git fehlt, folge der offiziellen Anleitung:
<https://git-scm.com/book/en/v2/Getting-Started-Installing-Git>

### 2. Projekt auschecken

Wechsle in einen Ordner deiner Wahl, z. B. `~/workspace`, und klone das
Repository. Das Projekt wird dabei als Unterordner `workshop-binastar-starter`
angelegt.

```bash
mkdir -p ~/workspace
cd ~/workspace
git clone git@github.com:thomas-negele/workshop-binastar-starter.git
cd workshop-binastar-starter
```

Im Terminal kurz prüfen:

```bash
git remote -v
```

Das ist in Ordnung, wenn `origin` mit der Repo-URL ausgegeben wird:

```text
origin  git@github.com:thomas-negele/workshop-binastar-starter.git (fetch)
origin  git@github.com:thomas-negele/workshop-binastar-starter.git (push)
```

### 3. Projekt in Eclipse importieren

1. Eclipse starten.
2. Im Menü **File → Import...** wählen.
3. **General → Existing Projects into Workspace** auswählen und **Next** klicken.
4. Bei **Select root directory** den Ordner `workshop-binastar-starter` auswählen,
   den du gerade ausgecheckt hast.
5. Das Projekt `workshop-binastar-starter` anhaken und **Finish** klicken.

Danach sollte das Projekt im **Package Explorer** sichtbar sein.

### 4. Terminal im Project Root öffnen

Wir arbeiten im Workshop direkt aus Eclipse heraus, damit das Terminal schon im
richtigen Projektordner steht.

1. Im **Package Explorer** mit der **rechten Maustaste** auf den Project Root
   `workshop-binastar-starter` klicken.
2. Im Kontextmenü **Show In → Terminal** wählen.
3. Unten öffnet sich die **Terminal**-View – bereits im Projektordner.

Alternativ kannst du dein gewohntes Terminal neben Eclipse verwenden – wichtig
ist nur, in deinem Projektverzeichnis zu sein (der Ordner, in den du das Repo
ausgecheckt hast).

### 5. Basis-Tools prüfen

Claude Code nutzt beim Arbeiten im Projekt normale Kommandozeilen-Tools zum
Suchen, Lesen und Bearbeiten von Dateien. Auf macOS und Linux sind die meisten
davon normalerweise schon vorhanden. Im Terminal prüfen:

```bash
command -v curl grep find sed
```

Falls auf Linux etwas fehlt:

```bash
sudo apt update
sudo apt install curl grep findutils sed
```

Optional, aber hilfreich für schnelle Code-Suche:

```bash
command -v rg || echo "ripgrep optional: grep und find reichen für den Workshop"
```

### 6. Claude Code einrichten und testen

Claude Code ist das KI-Kommandozeilen-Werkzeug, mit dem wir im Workshop arbeiten.
Installation und Anmeldung sind im offiziellen Quickstart-Guide von Anthropic
beschrieben – dort einmal durchgehen:

<https://code.claude.com/docs/en/quickstart#step-1-install-claude-code>

Falls `claude` direkt nach der Installation im Terminal nicht gefunden wird:
Terminal schließen, neu öffnen und nochmal prüfen.

**Test** – im Projektordner im Terminal starten:

```bash
claude --version
claude
```

Eine Frage eingeben, z. B.:

```text
Welches Model ist gerade eingestellt?
```

Antwortet Claude mit einer sinnvollen Antwort, also einem Modelnamen wie Sonnet, Opus oder Fable, funktioniert
alles. Mit `/exit` wieder verlassen.

### 7. Java (JDK) prüfen

Das Java-Programm wird direkt mit dem JDK kompiliert und ausgeführt – kein
Build-Tool nötig. Der PostgreSQL-Treiber wird bei Bedarf als JAR in den Ordner
`libs/` geladen.
Für die Kommandozeile wird ein **JDK 11 oder neuer** benötigt. Ein neueres JDK
ist in Ordnung; das Projekt wird beim Kompilieren auf Java 11 festgelegt.
Im Terminal prüfen:

```bash
java -version
javac -version
```

Beide sollten **11 oder höher** anzeigen. Wichtig ist, dass `javac` vorhanden
ist; eine reine JRE reicht nicht aus.

## Datenbank (PostgreSQL im Docker-Container)

Das Projekt bringt eine lokale PostgreSQL-Datenbank als Docker-Container mit
(siehe `docker-compose.yml`).

- **Image:** `postgres:16`
- **Container:** `binastar-starter-postgres`
- **Host / Port:** `localhost:5432`
- **Datenbank:** `binastar-starter`
- **Benutzer:** `binastar-starter`
- **Passwort:** `binastar-starter`
- **JDBC-URL:** `jdbc:postgresql://localhost:5432/binastar-starter`

> **Voraussetzung:** eine laufende Container-Runtime mit `docker compose`. Je nach Betriebssystem:
>
> - **macOS:** Docker läuft in einer Linux-VM. Üblich sind **Colima** (`colima start`)
>   oder **Docker Desktop** (App starten). Prüfen mit `docker info` – kommt eine
>   Ausgabe ohne Fehler, läuft die Runtime.
> - **Linux:** Die Docker-Engine läuft nativ als Daemon, kein Colima nötig. Falls
>   sie nicht läuft, mit `sudo systemctl start docker` starten.

### 8. Container starten und verwalten

Im Terminal im Project Root ausführen:

```bash
docker compose up -d      # DB starten (im Hintergrund)
docker compose ps         # Status anzeigen
```

Die DB-Daten liegen im Projektordner unter **`docker-data/postgres/`** (als
Bind-Mount, per `.gitignore` vom Commit ausgeschlossen) und bleiben über
Container-Neustarts hinweg erhalten.

Sobald bei `docker compose ps` in der Spalte `STATUS` der Zusatz **`(healthy)`**
erscheint (z. B. `Up About a minute (healthy)`), ist die Datenbank bereit.
`healthy` ist keine eigene Spalte und wird beim `docker compose up -d` noch nicht
angezeigt – dafür `docker compose ps` ausführen.

### 9. Treiber laden

Dieses Skript lädt den PostgreSQL-Treiber nach `libs/`, falls er lokal noch
nicht vorhanden ist.

Im Terminal im Project Root ausführen:

```bash
./scripts/download_dbdriver.sh
```

### 10. Testdaten importieren und prüfen

Dieses Skript legt die Tabelle `timetable` an, importiert die Testdaten und
fragt zur Kontrolle den ersten Workshop-Tag ab. Die Datenbank muss vorher laufen
und `healthy` sein.

Im Terminal im Project Root ausführen:

```bash
./scripts/insert_testdata.sh
```

Am Ende zeigt das Skript zur Kontrolle den ersten Workshop-Tag:

```text
     block     |              inhalt
---------------+-----------------------------------
 Vormittag I   | Kickoff & KI-Grundlagen
 Vormittag II  | KI im Alltag, Datenschutz & IP
 Nachmittag I  | Live-Demo: Agentische Entwicklung
 Nachmittag II | Skills – Agents & Guardrails
(4 rows)
```

## Java-Programm ausführen

Die `Timetable`-Klasse gibt eine Begrüßung aus und stößt anschließend das Laden und
Ausgeben des Workshop-Ablaufs aus der Datenbank (Tabelle `timetable`) an. Der
Ablauf wird tageweise im Terminal ausgegeben. **Voraussetzung:** Treiber und
Testdaten wurden vorbereitet (siehe vorige Abschnitte). Falls Eclipse das JAR
danach noch nicht sieht: Projekt markieren und mit **F5** aktualisieren.

### 11. Java-Programm in Eclipse ausführen

In Eclipse:

1. `Timetable.java` öffnen
2. Rechtsklick → **Run As → Java Application**

### 12. Java-Programm auf der Kommandozeile ausführen

Auf der Kommandozeile (im Projektordner):

```bash
# kompilieren (Ausgabe nach bin/)
javac --release 11 -cp "libs/*" -sourcepath src -d bin src/de/thomasnegele/workshop/binastar/starter/Timetable.java

# Timetable ausführen
java -cp "bin:libs/*" de.thomasnegele.workshop.binastar.starter.Timetable
```

Der PostgreSQL-Treiber (`libs/postgresql-42.7.4.jar`) ist auf dem Klassenpfad –
darüber verbindet sich das Programm mit der Datenbank.

Dann solltest du den Timetable für diese Woche in deinem Terminal sehen. Am Ende
der Ausgabe steht der Hinweis, dass sich Inhalte und Reihenfolge je nach
Geschwindigkeit, Fragen und Erkenntnissen der Gruppe anpassen können.

## Checkliste

Abhaken in dieser Reihenfolge:

- [ ] **01:** Git geprüft oder installiert (`git --version` funktioniert)
- [ ] **02:** Projekt in einem Ordner deiner Wahl ausgecheckt und Git-Remote geprüft (`git remote -v`)
- [ ] **03:** Projekt in Eclipse importiert und im Package Explorer sichtbar
- [ ] **04:** Terminal deiner Wahl im Project Root geöffnet (`pwd` endet auf deinem Projektverzeichnis)
- [ ] **05:** Basis-Tools geprüft (`curl`, `grep`, `find`, `sed`)
- [ ] **06:** Claude Code eingerichtet und getestet (`claude` im Projekt, Frage gestellt, mit `/exit` verlassen)
- [ ] **07:** Java geprüft (`java -version` und `javac -version` zeigen 11 oder höher)
- [ ] **08:** Datenbank-Container gestartet (`docker compose up -d`) und Status ist `healthy`
- [ ] **09:** PostgreSQL-Treiber geladen (`./scripts/download_dbdriver.sh`)
- [ ] **10:** Testdaten importiert (`./scripts/insert_testdata.sh`) und Kontrollabfrage zeigt den ersten Workshop-Tag
- [ ] **11:** `Timetable` in Eclipse über **Run As → Java Application** ausgeführt (gibt den Workshop-Ablauf in der Eclipse Console als Tabelle aus)
- [ ] **12:** `Timetable` auf der Kommandozeile kompiliert und ausgeführt (`javac …`, `java …`)
- [ ] **Abschluss:** Änderungen lassen sich in einem Git-Diff-Tool (oder dem eingebauten in Eclipse) anzeigen
- [ ] **Abschluss:** Dieses Markdown wird in Eclipse gut dargestellt und lässt sich bearbeiten

## Cleanup nach dem Workshop

Das aktuelle Projekt bleibt während des Workshops erhalten – hier werden die
Übungen ausgeführt. Nach dem Workshop kannst du es als Demo behalten oder den
Projektordner komplett löschen.

Orientierung zur aktuellen Größe auf diesem Rechner:

- Projektordner insgesamt: < 100 MB
- Docker-Image `postgres:16`: etwa 700 MB

Im Terminal im Project Root kann der Datenbank-Container gestoppt werden:

```bash
docker compose down
```

Wenn du die Demo nicht behalten möchtest, kannst du im Terminal stattdessen den
ganzen Projektordner löschen. Dazu zuerst aus dem Projektordner heraus wechseln:

```bash
cd ..
rm -rf workshop-binastar-starter/
```

Optional kann auch das heruntergeladene PostgreSQL-Image entfernt werden, wenn
es nicht in anderen Projekten genutzt wird. Im Terminal:

```bash
docker image rm postgres:16
```
