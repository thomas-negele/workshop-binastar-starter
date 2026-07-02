package de.thomasnegele.workshop.binastar.starter.customers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Schreibt Customer-Datensätze in die PostgreSQL-Datenbank.
 *
 * Duplikate (gleicher Vor- und Nachname) werden übersprungen: Dank des Unique-Constraints
 * uq_customers_name (firstname, lastname) genügt "ON CONFLICT ... DO NOTHING".
 */
public class CustomerRepository {

	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/workshop";
	private static final String DB_USER = "workshop";
	private static final String DB_PASSWORD = "workshop";

	private static final String INSERT_CUSTOMER =
			"INSERT INTO customers (firstname, lastname, email, birthdate, zip_code) "
			+ "VALUES (?, ?, ?, ?, ?) "
			+ "ON CONFLICT (firstname, lastname) DO NOTHING";

	/**
	 * Importiert die übergebenen Kunden.
	 *
	 * @return Anzahl der tatsächlich eingefügten Datensätze (ohne übersprungene Duplikate)
	 */
	public int importCustomers(List<Customer> customers) {
		int inserted = 0;

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(INSERT_CUSTOMER)) {

			conn.setAutoCommit(false);
			for (Customer customer : customers) {
				stmt.setString(1, customer.getFirstname());
				stmt.setString(2, customer.getLastname());
				stmt.setString(3, customer.getEmail());
				if (customer.getBirthdate() != null) {
					stmt.setDate(4, Date.valueOf(customer.getBirthdate()));
				} else {
					stmt.setNull(4, Types.DATE);
				}
				if (customer.getZipCode() != null) {
					stmt.setString(5, customer.getZipCode());
				} else {
					stmt.setNull(5, Types.VARCHAR);
				}

				inserted += stmt.executeUpdate();
			}
			conn.commit();

		} catch (SQLException e) {
			System.err.println("Datenbankfehler beim Import: " + e.getMessage());
			return inserted;
		}

		return inserted;
	}

}
