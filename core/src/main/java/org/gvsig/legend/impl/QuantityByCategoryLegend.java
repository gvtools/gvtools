package org.gvsig.legend.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.LegendType;

public class QuantityByCategoryLegend extends AbstractLegend {
	public static final String TYPE = "QUANTITY_BY_CATEGORY";

	private IntervalLegend colorLegend;
	private SizeIntervalLegend sizeLegend;

	QuantityByCategoryLegend() {
	}

	public void init(Layer layer, IntervalLegend colorLegend,
			SizeIntervalLegend sizeLegend) {
		super.setLayer(layer);
		this.colorLegend = colorLegend;
		this.sizeLegend = sizeLegend;
	}

	public IntervalLegend getColorIntervalLegend() {
		return colorLegend;
	}

	public SizeIntervalLegend getSizeIntervalLegend() {
		return sizeLegend;
	}

	public Symbolizer[] getSymbols() throws IOException {
		List<Symbolizer> list = new ArrayList<Symbolizer>();
		Collections.addAll(list, colorLegend.getSymbols());
		Collections.addAll(list, sizeLegend.getSymbols());
		return list.toArray(new Symbolizer[list.size()]);
	}

	public Interval[] getIntervals() throws IOException {
		List<Interval> list = new ArrayList<Interval>();
		Collections.addAll(list, colorLegend.getIntervals());
		Collections.addAll(list, sizeLegend.getIntervals());
		return list.toArray(new Interval[list.size()]);
	}

	@Override
	protected Style createStyle() throws IOException {
		Style style = styleFactory.createStyle();
		List<FeatureTypeStyle> styles = style.featureTypeStyles();
		styles.addAll(sizeLegend.createStyle().featureTypeStyles());
		styles.addAll(colorLegend.createStyle().featureTypeStyles());
		return style;
	}

	@Override
	public LegendType getXML() throws PersistenceException {
		// TODO Auto-generated method stub
		return super.getXML();
	}

	@Override
	public void setXML(LegendType xml) {
		// TODO Auto-generated method stub
		super.setXML(xml);
	}
}
