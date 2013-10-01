package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class IntervalLegend extends AbstractIntervalLegend {
	private Color start, end;

	@AssistedInject
	public IntervalLegend(@Assisted("start") Color start,
			@Assisted("end") Color end, @Assisted Type intervalType,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Layer layer, @Assisted String fieldName,
			@Assisted int nIntervals) {
		super(intervalType, defaultSymbol, useDefault, layer, fieldName,
				nIntervals);
		this.start = start;
		this.end = end;
	}

	@AssistedInject
	public IntervalLegend(@Assisted Map<Interval, Symbolizer> symbolsMap,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Layer layer, @Assisted String fieldName) {
		super(symbolsMap, defaultSymbol, useDefault, layer, fieldName);
	}

	private Color getColor(Symbolizer symbolizer) {
		if (symbolizer instanceof PointSymbolizer) {
			return SLD.color((PointSymbolizer) symbolizer);
		} else if (symbolizer instanceof LineSymbolizer) {
			return SLD.color((LineSymbolizer) symbolizer);
		} else if (symbolizer instanceof PolygonSymbolizer) {
			return SLD.color(((PolygonSymbolizer) symbolizer).getFill());
		} else {
			throw new IllegalArgumentException("bug! Unrecognized symbolizer");
		}
	}

	@Override
	protected Style createStyle() throws IOException {
		Style style = super.createStyle();

		Interval[] intervals = getIntervals();
		this.nIntervals = intervals.length;
		if (intervals.length > 0) {
			this.start = getColor(symbolsMap().get(intervals[0]));
			this.end = getColor(symbolsMap().get(
					intervals[intervals.length - 1]));
		}

		return style;
	}

	@Override
	protected Symbolizer[] getSymbolsForInterval(Interval interval)
			throws IOException {
		return new Symbolizer[] { symbolsMap().get(interval) };
	}

	public Color getStartColor() {
		return start;
	}

	public Color getEndColor() {
		return end;
	}

	@Override
	protected Map<Interval, Symbolizer> getSymbols(Interval[] intervals) {
		double r = start.getRed();
		double g = start.getGreen();
		double b = start.getBlue();
		double stepR = (end.getRed() - r) / (nIntervals - 1.0);
		double stepG = (end.getGreen() - g) / (nIntervals - 1.0);
		double stepB = (end.getBlue() - b) / (nIntervals - 1.0);

		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();

		for (Interval interval : intervals) {
			int red = Math.max(0, Math.min(255, (int) r));
			int green = Math.max(0, Math.min(255, (int) g));
			int blue = Math.max(0, Math.min(255, (int) b));
			Symbolizer symbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), new Color(red, green, blue),
					interval.toString());

			r += stepR;
			g += stepG;
			b += stepB;

			symbolsMap.put(interval, symbol);
		}

		return symbolsMap;
	}
}
