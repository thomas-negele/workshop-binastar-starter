-- Workshop Demo: Customer Import Schema
-- Gleiche schema.sql für Repo 1 (leer) und Repo 2 (gefüllt)

CREATE TABLE IF NOT EXISTS customers (
    id          SERIAL PRIMARY KEY,
    firstname   VARCHAR(100)  NOT NULL,
    lastname    VARCHAR(100)  NOT NULL,
    email       VARCHAR(255)  NOT NULL,
    birthdate   DATE,                      -- nullable: ungültige Werte werden als NULL gespeichert
    zip_code    VARCHAR(10),               -- nullable: "unbekannt", Buchstaben etc. → NULL
    imported_at TIMESTAMP DEFAULT NOW()
);

-- Unique constraint: firstname+lastname-Kombination darf nur einmal vorkommen
ALTER TABLE customers
    ADD CONSTRAINT uq_customers_name UNIQUE (firstname, lastname);

-- Fehler-Log für Validierungsprobleme (optional, wenn die App das schreiben soll)
-- CREATE TABLE import_errors (
--     id          SERIAL PRIMARY KEY,
--     row_number  INTEGER,
--     firstname   TEXT,
--     lastname    TEXT,
--     email       TEXT,
--     reason      TEXT,
--     logged_at   TIMESTAMP DEFAULT NOW()
-- );
