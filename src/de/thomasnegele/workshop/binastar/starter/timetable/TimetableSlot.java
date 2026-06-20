package de.thomasnegele.workshop.binastar.starter.timetable;

public class TimetableSlot {

	private final int dayNumber;
	private final String dayName;
	private final int blockNumber;
	private final String blockName;
	private final String content;

	public TimetableSlot(int dayNumber, String dayName, int blockNumber, String blockName, String content) {
		this.dayNumber = dayNumber;
		this.dayName = dayName;
		this.blockNumber = blockNumber;
		this.blockName = blockName;
		this.content = content;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public String getDayName() {
		return dayName;
	}

	public int getBlockNumber() {
		return blockNumber;
	}

	public String getBlockName() {
		return blockName;
	}

	public String getContent() {
		return content;
	}

}
