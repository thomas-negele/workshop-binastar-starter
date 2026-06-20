package de.thomasnegele.workshop.binastar.starter.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TimetablePrinter {

	public void print(List<TimetableSlot> slots) {
		if (slots.isEmpty()) {
			System.out.println("(Konnte den Workshop-Ablauf nicht laden – läuft der "
					+ "Datenbank-Container und ist die Tabelle 'timetable' befüllt?)");
			return;
		}

		printByDay(toRows(slots));
		System.out.println("* Inhalte und Reihenfolge können sich je nach Geschwindigkeit, Fragen und "
				+ "Erkenntnissen der Gruppe anpassen.");
	}

	private List<String[]> toRows(List<TimetableSlot> slots) {
		TreeMap<Integer, String> days = new TreeMap<>();
		TreeMap<Integer, String> blocks = new TreeMap<>();
		Map<String, String> cells = new HashMap<>();

		for (TimetableSlot slot : slots) {
			days.putIfAbsent(slot.getDayNumber(), slot.getDayName());
			blocks.putIfAbsent(slot.getBlockNumber(), slot.getBlockName());
			cells.put(slot.getBlockNumber() + "|" + slot.getDayNumber(), slot.getContent());
		}

		List<String[]> rows = new ArrayList<>();
		List<String> header = new ArrayList<>();
		header.add("Einheit");
		header.addAll(days.values());
		rows.add(header.toArray(new String[0]));

		for (Map.Entry<Integer, String> block : blocks.entrySet()) {
			List<String> row = new ArrayList<>();
			row.add(block.getValue());
			for (Integer dayNumber : days.keySet()) {
				row.add(cells.getOrDefault(block.getKey() + "|" + dayNumber, ""));
			}
			rows.add(row.toArray(new String[0]));
		}

		return rows;
	}

	private void printByDay(List<String[]> rows) {
		String[] header = rows.get(0);
		List<String[]> blocks = rows.subList(1, rows.size());

		int slotWidth = 0;
		for (String[] block : blocks) {
			slotWidth = Math.max(slotWidth, block[0].length());
		}

		for (int day = 1; day < header.length; day++) {
			System.out.println(header[day]);
			for (String[] block : blocks) {
				String content = day < block.length ? block[day] : "";
				System.out.println("  " + pad(block[0], slotWidth) + "   " + content);
			}
			System.out.println();
		}
	}

	private String pad(String value, int width) {
		return value.length() >= width ? value : value + " ".repeat(width - value.length());
	}

}
