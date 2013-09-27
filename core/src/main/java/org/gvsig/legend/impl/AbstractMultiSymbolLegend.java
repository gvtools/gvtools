package org.gvsig.legend.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.layer.Selection;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Legend;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import com.google.inject.assistedinject.Assisted;

public abstract class AbstractMultiSymbolLegend implements Legend {
	private Rule selectionRule;
	private Map<Rule, Object> ruleValues;

	private boolean useDefault;
	private Symbolizer defaultSymbol;
	protected Layer layer;

	@Inject
	private StyleFactory styleFactory;

	@Inject
	protected FilterFactory2 filterFactory;

	@Inject
	protected DefaultSymbols defaultSymbols;

	@Inject
	public AbstractMultiSymbolLegend(@Assisted Layer layer,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault) {
		this.layer = layer;
		this.defaultSymbol = defaultSymbol;
		this.useDefault = useDefault;
	}

	@Override
	public Style getStyle() {
		Object[] keys = getSymbolizerKeys();
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle();

		Filter selectionFilter = filterFactory.id(layer.getSelection());
		ruleValues = new HashMap<Rule, Object>();

		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i];

			Rule rule = styleFactory.createRule();
			rule.symbolizers().add(getSymbol(value));
			rule.setFilter(doGetFilter(value, selectionFilter));
			rule.setElseFilter(false);

			fts.rules().add(rule);
			ruleValues.put(rule, value);
		}

		if (useDefault && defaultSymbol != null) {
			Rule rule = styleFactory.createRule();
			rule.symbolizers().add(defaultSymbol);
			rule.setElseFilter(true);
			fts.rules().add(rule);
		}

		selectionRule = styleFactory.createRule();
		selectionRule.symbolizers().add(
				defaultSymbols.createDefaultSymbol(layer.getShapeType(),
						Color.yellow, null));
		selectionRule.setFilter(selectionFilter);
		fts.rules().add(selectionRule);

		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}

	public boolean useDefaultSymbol() {
		return useDefault;
	}

	public Symbolizer getDefaultSymbol() {
		return defaultSymbol;
	}

	/**
	 * Gets the symbolizer for the given key.
	 * 
	 * @param key
	 *            The key to obtain the symbolizer. It will always be one of the
	 *            objects returned by {@link #getSymbolizerKeys()}, so it is
	 *            safe to cast it to the type of objects returned by
	 *            {@link #getSymbolizerKeys()}.
	 * @return the symbolizer for the style rule
	 */
	protected abstract Symbolizer getSymbol(Object key);

	/**
	 * Gets the array of values to style.
	 * 
	 * @return the array of values to style.
	 */
	protected abstract Object[] getSymbolizerKeys();

	/**
	 * Gets the filter for the given key.
	 * 
	 * @param key
	 *            The key to obtain the filter. It will always be one of the
	 *            objects returned by {@link #getSymbolizerKeys()}, so it is
	 *            safe to cast it to the type of objects returned by
	 *            {@link #getSymbolizerKeys()}.
	 * @return the filter for the style rule
	 */
	protected abstract Filter getFilter(Object key);

	@Override
	public void updateSelection(Selection selection) {
		Filter selectionFilter = filterFactory.id(selection);
		selectionRule.setFilter(selectionFilter);
		for (Rule rule : ruleValues.keySet()) {
			rule.setFilter(doGetFilter(ruleValues.get(rule), selectionFilter));
		}
	}

	private Filter doGetFilter(Object key, Filter selectionFilter) {
		return filterFactory.and(getFilter(key),
				filterFactory.not(selectionFilter));
	}
}
