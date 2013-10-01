package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class SingleSymbolLegend extends AbstractLegend {
	private Symbolizer symbol;

	@AssistedInject
	public SingleSymbolLegend(@Assisted Layer layer,
			DefaultSymbols defaultSymbols) {
		this(layer, null, defaultSymbols);
	}

	@AssistedInject
	public SingleSymbolLegend(@Assisted Layer layer,
			@Assisted Symbolizer symbol, DefaultSymbols defaultSymbols) {
		super(layer);
		if (symbol != null) {
			this.symbol = symbol;
		} else {
			this.symbol = defaultSymbols.createDefaultSymbol(
					layer.getShapeType(), Color.blue, null);
		}
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
