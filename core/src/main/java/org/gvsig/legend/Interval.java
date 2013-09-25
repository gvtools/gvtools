package org.gvsig.legend;

import java.text.NumberFormat;

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
		return NumberFormat.getInstance().format(min) + " - "
				+ NumberFormat.getInstance().format(max);
	}
}
