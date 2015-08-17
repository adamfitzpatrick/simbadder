package net.muneris.simbadder.simbadapi.query;

import java.util.Arrays;

public enum RadiusUnit {
	DEGREES("d"),
	MINUTES("m"),
	SECONDS("s");
	
	private static final String[] D_ALTERNATE = {"deg","degs","degree","degrees"};
	private static final String[] M_ALTERNATE = {"min","mins","minute","minutes"};
	private static final String[] S_ALTERNATE = {"sec", "secs","second","seconds"};
	private String unit;
	
	public static RadiusUnit parseString(String unit) {
		if (unit.length() == 1) {
			return parseSingleLetter(unit);
		}
		String leader = unit.substring(0, 2);
		if (leader.equals("de") && arrayMatcher(unit, D_ALTERNATE)) {
			return RadiusUnit.DEGREES;
		} else if (leader.equals("mi") && arrayMatcher(unit, M_ALTERNATE)) {
			return RadiusUnit.MINUTES;
		} else if (leader.equals("se") && arrayMatcher(unit, S_ALTERNATE)) {
			return RadiusUnit.SECONDS;
		}
		return null;
	}
	
	private RadiusUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return unit;
	}
	
	private static RadiusUnit parseSingleLetter(String unit) {
		if (unit.equals(RadiusUnit.DEGREES.toString())) {
			return RadiusUnit.DEGREES;
		} else if (unit.equals(RadiusUnit.MINUTES.toString())) {
			return RadiusUnit.MINUTES;
		} else if (unit.equals(RadiusUnit.SECONDS.toString())) {
			return RadiusUnit.SECONDS;
		}
		return null;
	}
	
	private static boolean arrayMatcher(String string, String[] array) {
		return Arrays.asList(array).stream().anyMatch(arr -> arr.equals(string));
	}
}
