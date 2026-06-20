package de.thomasnegele.workshop.binastar.starter.timetable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TimetableRepository {

	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/binastar-starter";
	private static final String DB_USER = "binastar-starter";
	private static final String DB_PASSWORD = "binastar-starter";
	private static final String SELECT_TIMETABLE =
			"SELECT tag_nr, tag, block_nr, block, inhalt FROM timetable";

	public List<TimetableSlot> loadTimetable() {
		List<TimetableSlot> slots = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_TIMETABLE)) {
			while (rs.next()) {
				slots.add(new TimetableSlot(
						rs.getInt("tag_nr"),
						rs.getString("tag"),
						rs.getInt("block_nr"),
						rs.getString("block"),
						rs.getString("inhalt")));
			}
		} catch (SQLException e) {
			System.err.println("Datenbankfehler: " + e.getMessage());
			return List.of();
		}

		return slots;
	}

}
