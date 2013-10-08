package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.LegendType;
import org.gvsig.persistence.generated.StringPropertyType;

public class IntervalLegend extends AbstractIntervalLegend {
	public static final String TYPE = "COLOR_INTERVAL";

	private Color start, end;

	IntervalLegend() {
	}

	public void init(Color start, Color end, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName, int nIntervals) {
		super.initialize(intervalType, defaultSymbol, useDefault, layer,
				fieldName, nIntervals);
		this.start = start;
		this.end = end;
	}

	public void init(Map<Interval, Symbolizer> symbolsMap, Type intervalType,
			Symbolizer defaultSymbol, boolean useDefault, Layer layer,
			String fieldName) throws IOException {
		super.initialize(symbolsMap, intervalType, defaultSymbol, useDefault,
				layer, fieldName);

		Interval[] intervals = getIntervals();
		this.nIntervals = intervals.length;
		if (intervals.length > 0) {
			this.start = getColor(symbolsMap().get(intervals[0]));
			this.end = getColor(symbolsMap().get(
					intervals[intervals.length - 1]));
		}
	}

	public void init(Layer layer, String fieldName) {
		super.initialize(layer, fieldName);
		this.start = Color.red;
		this.end = Color.blue;
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
			Symbolizer symbol = defaultSymbols.createDefaultSymbol(getLayer()
					.getShapeType(), new Color(red, green, blue), interval
					.toString());

			r += stepR;
			g += stepG;
			b += stepB;

			symbolsMap.put(interval, symbol);
		}

		return symbolsMap;
	}

	@Override
	public void setXML(LegendType xml) {
		assert xml.getType().equals(TYPE);
		super.setXML(xml);
		for (StringPropertyType property : xml.getProperties()) {
			String name = property.getPropertyName();
			String value = property.getPropertyValue();
			if (name.equals("start-color")) {
				this.start = new Color(Integer.parseInt(value));
			} else if (name.equals("end-color")) {
				this.end = new Color(Integer.parseInt(value));
			}
		}
	}

	@Override
	public LegendType getXML() throws PersistenceException {
		LegendType xml = super.getXML();
		xml.setType(TYPE);
		xml.getProperties().add(
				property("start-color", Integer.toString(start.getRGB())));
		xml.getProperties().add(
				property("end-color", Integer.toString(end.getRGB())));
		return xml;
	}
}
