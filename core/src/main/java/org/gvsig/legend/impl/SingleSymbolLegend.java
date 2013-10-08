package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.LegendType;
import org.gvsig.persistence.generated.StringPropertyType;

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

	@Override
	public LegendType getXML() throws PersistenceException {
		LegendType xml = super.getXML();
		xml.setType(TYPE);
		xml.getProperties().add(symbol("symbol", symbol));
		return xml;
	}

	@Override
	public void setXML(LegendType xml) {
		assert xml.getType().equals(TYPE);

		super.setXML(xml);

		for (StringPropertyType property : xml.getProperties()) {
			if (property.getPropertyName().equals("symbol")) {
				symbol = decode(property.getPropertyValue());
				break;
			}
		}
	}
}
