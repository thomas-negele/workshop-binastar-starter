package de.thomasnegele.workshop.binastar.starter;

import java.util.List;

import de.thomasnegele.workshop.binastar.starter.timetable.*;

public class Timetable {
	public static void main(String[] args) {
		System.out.println("Willkommen beim AI und Agile Workshop für BinaStar!");
		System.out.println();

		TimetableRepository repository = new TimetableRepository();
		List<TimetableSlot> slots = repository.loadTimetable();
		TimetablePrinter printer = new TimetablePrinter();
		printer.print(slots);
	}

}
