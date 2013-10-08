package org.gvsig.legend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interval {
	private double min;
	private double max;

	public Interval(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	@Override
	public String toString() {
		return min + " - " + max;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Interval) {
			Interval other = (Interval) obj;
			return other.min == this.min && other.max == this.max;
		} else {
			return false;
		}
	}

	public static Interval parseInterval(String s)
			throws IllegalArgumentException {
		try {
			Pattern pattern = Pattern.compile("\\s*(\\S+)\\s+"
					+ "-\\s+(\\S+)\\s*");
			Matcher matcher = pattern.matcher(s);
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Invalid interval string: "
						+ s);
			}
			double min = Double.parseDouble(matcher.group(1));
			double max = Double.parseDouble(matcher.group(2));
			return new Interval(min, max);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
