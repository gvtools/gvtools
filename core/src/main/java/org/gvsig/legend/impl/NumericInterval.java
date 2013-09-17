package org.gvsig.legend.impl;

import org.gvsig.legend.Interval;

public class NumericInterval implements Interval {
	private double min;
	private double max;

	public NumericInterval(double min, double max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public double getMin() {
		return min;
	}

	@Override
	public double getMax() {
		return max;
	}
}
