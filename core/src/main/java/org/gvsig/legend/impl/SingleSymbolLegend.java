package org.gvsig.legend.impl;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.gvsig.legend.Legend;

public class SingleSymbolLegend implements Legend {
	private Rule defaultRule, selectionRule;
	private StyleFactory styleFactory;

	public SingleSymbolLegend(Rule defaultRule, Rule selectionRule,
			StyleFactory styleFactory) {
		this.defaultRule = defaultRule;
		this.selectionRule = selectionRule;
		this.styleFactory = styleFactory;
	}

	@Override
	public Style getStyle() {
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[] {
				defaultRule, selectionRule });
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}
}
