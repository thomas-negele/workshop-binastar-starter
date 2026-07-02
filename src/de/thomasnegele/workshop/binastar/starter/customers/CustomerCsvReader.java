package de.thomasnegele.workshop.binastar.starter.customers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Liest customers.csv ein und wandelt jede Zeile in ein {@link Customer}-Objekt um.
 *
 * Robustes Parsen der "schmutzigen" Daten:
 *  - birthdate in verschiedenen Formaten (ISO, dd.MM.yyyy, dd-MM-yyyy, Unix-Timestamp).
 *    Alles, was sich nicht als gültiges Datum interpretieren lässt, wird zu null.
 *  - zip_code: nur reine Ziffernfolgen (3-10 Stellen, nicht nur Nullen) werden übernommen,
 *    sonst null.
 */
public class CustomerCsvReader {

	private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter GERMAN_DOT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter DASH_DMY = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public List<Customer> read(Path csvFile) {
		List<Customer> customers = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {
			String line = reader.readLine(); // Kopfzeile überspringen
			int rowNumber = 1;
			while ((line = reader.readLine()) != null) {
				rowNumber++;
				if (line.isBlank()) {
					continue;
				}

				String[] fields = line.split(",", -1);
				if (fields.length < 3) {
					System.err.println("Zeile " + rowNumber + " übersprungen (zu wenige Spalten): " + line);
					continue;
				}

				String firstname = fields[0].trim();
				String lastname = fields[1].trim();
				String email = fields[2].trim();
				if (firstname.isEmpty() || lastname.isEmpty()) {
					System.err.println("Zeile " + rowNumber + " übersprungen (Name fehlt): " + line);
					continue;
				}

				LocalDate birthdate = parseBirthdate(fields.length > 3 ? fields[3].trim() : "");
				String zipCode = parseZipCode(fields.length > 4 ? fields[4].trim() : "");

				customers.add(new Customer(firstname, lastname, email, birthdate, zipCode));
			}
		} catch (IOException e) {
			System.err.println("CSV konnte nicht gelesen werden: " + e.getMessage());
			return List.of();
		}

		return customers;
	}

	/**
	 * Versucht, das Geburtsdatum aus den verschiedenen Formaten der CSV zu lesen.
	 * Gibt null zurück, wenn kein gültiges Datum erkannt wird.
	 */
	private LocalDate parseBirthdate(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		for (DateTimeFormatter formatter : new DateTimeFormatter[] { ISO_DATE, GERMAN_DOT, DASH_DMY }) {
			try {
				return LocalDate.parse(value, formatter);
			} catch (Exception ignored) {
				// nächstes Format versuchen
			}
		}

		// Unix-Timestamp in Sekunden (z. B. -302486400 oder 156470400)
		try {
			long epochSeconds = Long.parseLong(value);
			return Instant.ofEpochSecond(epochSeconds).atZone(ZoneOffset.UTC).toLocalDate();
		} catch (Exception ignored) {
			// kein Timestamp
		}

		return null;
	}

	/**
	 * Übernimmt nur plausible Postleitzahlen (reine Ziffern, 3-10 Stellen, nicht nur Nullen).
	 * "unbekannt", "??", Buchstaben etc. werden zu null.
	 */
	private String parseZipCode(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		if (!value.matches("\\d{3,10}")) {
			return null;
		}
		if (value.chars().allMatch(c -> c == '0')) {
			return null;
		}
		return value;
	}

}
