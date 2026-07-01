# Setup – Gruppen-Task (Do N2)

> Euer Startpunkt für den Nachmittag. Dauert ca. 5 Minuten.

---

## 1. Branch auschecken

```bash
cd ~/eclipse-workspace/workshop-binastar-starter
git checkout workshop/grouptask
```

---

## 2. OpenSpec installieren

OpenSpec ist das Tool das wir für Spec-Driven Development nutzen – einmalig global installieren:

```bash
npm install -g @fission-ai/openspec@latest
```

Prüfen ob es geklappt hat:
```bash
openspec --version
```

> **Node.js nötig:** Falls `npm` nicht gefunden wird – Node.js 20+ installieren via https://nodejs.org

---

## 3. Datenbank starten

```bash
docker compose up -d
```

Warten bis die DB bereit ist (~20 Sekunden):
```bash
docker compose ps   # Status sollte "healthy" zeigen
```

Die DB ist bereits gefüllt – 91 importierte Kundendatensätze aus dem CSV-Import der Demo.

Kurzer Check:
```bash
docker exec -it workshop-db psql -U workshop -d workshop \
  -c "SELECT COUNT(*) FROM customers;"
# → 91
```

---

## 4. Claude Code starten

```bash
claude
```

---

## 5. Eure Aufgabe – Spec schreiben

Ihr schreibt eine `requirements.md` (via OpenSpec) für einen **Verification Report**:

> Der Report soll zeigen was beim Import passiert ist: wie viele Datensätze importiert, wie viele Duplikate übersprungen, wie viele Validierungsfehler.

Startet mit:
```
/opsx:propose "Verification Report für den CSV-Import: Wie viele Datensätze wurden importiert? Wie viele Duplikate übersprungen? Wie viele Validierungsfehler? Report als CLI-Output oder HTML."
```

OpenSpec erzeugt dann einen Spec-Ordner unter `openspec/changes/verification-report/`.

**Schaut euch die generierten Dateien an und passt sie an eure Vorstellung an** bevor ihr implementiert.

---

## 6. Implementieren

Wenn eure Spec steht:
```
/opsx:apply
```

---

## Troubleshooting

**Port 5432 belegt:**
```bash
docker compose down && docker compose up -d
```

**Claude Code startet nicht:**
```bash
claude --version   # muss ≥ 1.x sein
```

**OpenSpec-Commands nicht verfügbar:**
```bash
openspec update    # aktualisiert die Slash-Commands im Projekt
```
