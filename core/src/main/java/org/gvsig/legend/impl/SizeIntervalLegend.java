package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.styling.DescriptionImpl;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;

public class SizeIntervalLegend extends AbstractIntervalLegend {
	private Interval size;
	private Symbolizer template, background;

	SizeIntervalLegend() {
	}

	public void init(Interval size, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName, int nIntervals, Symbolizer template,
			Symbolizer background) {
		super.initialize(intervalType, defaultSymbol, useDefault, layer,
				fieldName, nIntervals);
		this.size = size;
		this.template = template;
		this.background = background;
	}

	public void init(Map<Interval, Symbolizer> symbolsMap, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName, Symbolizer background) throws IOException {
		super.initialize(symbolsMap, intervalType, defaultSymbol, useDefault,
				layer, fieldName);
		this.background = background;

		double min = Double.MAX_VALUE;
		double max = -1;

		for (Symbolizer symbol : getSymbols()) {
			int s = -1;
			if (symbol instanceof PointSymbolizer) {
				s = SLD.pointSize((PointSymbolizer) symbol);
			} else if (symbol instanceof LineSymbolizer) {
				s = SLD.width((LineSymbolizer) symbol);
			} else if (symbol instanceof PolygonSymbolizer) {
				s = SLD.width(((PolygonSymbolizer) symbol).getStroke());
			}
			if (s < 0) {
				continue;
			}
			if (s < min) {
				min = s;
			}
			if (s > max) {
				max = s;
			}
		}

		this.size = new Interval(min, max);
	}

	public void init(Layer layer, String fieldName) {
		super.initialize(layer, fieldName);
		this.size = new Interval(1, 7);
		this.background = null;
		this.template = defaultSymbols.createDefaultSymbol(
				layer.getShapeType(), Color.lightGray, "");
	}

	public Interval getSize() {
		return size;
	}

	public Symbolizer getBackground() {
		return background;
	}

	public boolean useBackground() {
		return background != null;
	}

	@Override
	protected Symbolizer[] getSymbolsForInterval(Interval interval)
			throws IOException {
		Symbolizer symbol = symbolsMap().get(interval);
		if (background != null) {
			return new Symbolizer[] { background, symbol };
		} else {
			return new Symbolizer[] { symbol };
		}
	}

	@Override
	protected Map<Interval, Symbolizer> getSymbols(Interval[] intervals) {
		double range = size.getMax() - size.getMin();
		double step = range / nIntervals;

		Map<Interval, Symbolizer> symbolsMap = new HashMap<Interval, Symbolizer>();

		double symbolSize = size.getMin();
		for (Interval interval : intervals) {
			ResizeCopyStyleVisitor visitor = new ResizeCopyStyleVisitor(
					filterFactory.literal(symbolSize), styleFactory,
					filterFactory);
			template.accept(visitor);
			Symbolizer copy = (Symbolizer) visitor.getCopy();
			copy.setDescription(new DescriptionImpl(interval.toString(),
					interval.toString()));
			symbolsMap.put(interval, copy);
			symbolSize += step;
		}

		return symbolsMap;
	}
}
