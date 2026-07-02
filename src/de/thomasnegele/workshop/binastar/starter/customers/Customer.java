package de.thomasnegele.workshop.binastar.starter.customers;

import java.time.LocalDate;

/**
 * Ein Datensatz aus der customers.csv.
 * birthdate und zipCode dürfen null sein (ungültige Werte werden als NULL gespeichert).
 */
public class Customer {

	private final String firstname;
	private final String lastname;
	private final String email;
	private final LocalDate birthdate;
	private final String zipCode;

	public Customer(String firstname, String lastname, String email, LocalDate birthdate, String zipCode) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthdate = birthdate;
		this.zipCode = zipCode;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public String getZipCode() {
		return zipCode;
	}

}
