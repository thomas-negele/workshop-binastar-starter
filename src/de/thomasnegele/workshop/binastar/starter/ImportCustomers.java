package de.thomasnegele.workshop.binastar.starter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.thomasnegele.workshop.binastar.starter.customers.Customer;
import de.thomasnegele.workshop.binastar.starter.customers.CustomerCsvReader;
import de.thomasnegele.workshop.binastar.starter.customers.CustomerRepository;

/**
 * Importiert customers.csv in die PostgreSQL-Datenbank.
 * Duplikate (gleicher Vor- und Nachname) werden übersprungen.
 *
 * Optionales Argument: Pfad zur CSV-Datei (Standard: customers.csv im Projektverzeichnis).
 */
public class ImportCustomers {

	public static void main(String[] args) {
		Path csvFile = Paths.get(args.length > 0 ? args[0] : "customers.csv");

		System.out.println("Lese Kunden aus: " + csvFile.toAbsolutePath());
		CustomerCsvReader reader = new CustomerCsvReader();
		List<Customer> customers = reader.read(csvFile);
		System.out.println(customers.size() + " Datensätze aus der CSV gelesen.");

		CustomerRepository repository = new CustomerRepository();
		int inserted = repository.importCustomers(customers);

		System.out.println("Import abgeschlossen: " + inserted + " neu eingefügt, "
				+ (customers.size() - inserted) + " übersprungen (Duplikate).");
	}

}
