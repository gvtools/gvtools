package org.gvsig.layer.impl;

import java.awt.Color;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.gvsig.layer.SymbolFactoryFacade;
import org.opengis.filter.FilterFactory2;

public class SymbolFactoryFacadeImpl implements SymbolFactoryFacade {

	@Override
	public Style newLineStyle(Color color, int width) {
		StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
		FilterFactory2 filterFactory = CommonFactoryFinder.getFilterFactory2();

		Stroke stroke = styleFactory.createStroke(
				filterFactory.literal(Color.BLUE), filterFactory.literal(1));

		LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

		Rule rule = styleFactory.createRule();
		rule.symbolizers().add(sym);
		FeatureTypeStyle fts = styleFactory
				.createFeatureTypeStyle(new Rule[] { rule });
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}

}
