package org.gvsig.legend;

import java.awt.Color;

import org.geotools.styling.Symbolizer;

public interface IntervalLegend extends Legend {
	enum Type {
		EQUAL, NATURAL, QUANTILE
	}

	boolean usesDefaultSymbol();

	void setUseDefault(boolean useDefault);

	Interval[] getIntervals();

	Symbolizer[] getSymbols();

	Color getStartColor();

	Color getEndColor();

	Symbolizer getDefaultSymbol();

	Type getType();
}
