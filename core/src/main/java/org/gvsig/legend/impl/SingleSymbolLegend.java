package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;

public class SingleSymbolLegend extends AbstractLegend {
	public static final String TYPE = "SINGLE_SYMBOL";

	private Symbolizer symbol;

	SingleSymbolLegend() {
	}

	public void init(Layer layer) {
		super.setLayer(layer);
		this.symbol = defaultSymbols.createDefaultSymbol(layer.getShapeType(),
				Color.blue, null);
	}

	public void init(Layer layer, Symbolizer symbol) {
		super.setLayer(layer);
		this.symbol = symbol;
	}

	public Symbolizer getSymbol() {
		return symbol;
	}

	@Override
	protected Style createStyle() throws IOException {
		FeatureTypeStyle featureTypeStyle = featureTypeStyle(rule(symbol));
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(featureTypeStyle);
		return style;
	}
}
