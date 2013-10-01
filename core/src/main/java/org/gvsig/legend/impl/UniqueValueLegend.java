package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.opengis.filter.Filter;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.vividsolutions.jts.geom.Geometry;

public class UniqueValueLegend extends AbstractDefaultSymbolLegend {
	private String fieldName;
	private Map<Object, Symbolizer> symbolsMap;
	private Object[] values;
	private Symbolizer[] symbols;
	private Color[] colorScheme;
	private Comparator<Object> order;

	@AssistedInject
	public UniqueValueLegend(@Assisted Layer layer, @Assisted String fieldName,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Color[] colorScheme,
			@Assisted Map<Object, Symbolizer> symbols,
			@Assisted Comparator<Object> order) {
		super(layer, defaultSymbol, useDefault);
		this.fieldName = fieldName;
		this.symbolsMap = symbols;
		this.order = order;
		this.colorScheme = colorScheme;
	}

	@AssistedInject
	public UniqueValueLegend(@Assisted Layer layer, @Assisted String fieldName,
			@Assisted Symbolizer defaultSymbol, @Assisted boolean useDefault,
			@Assisted Color[] colorScheme, @Assisted Comparator<Object> order,
			DefaultSymbols defaultSymbols) {
		super(layer, defaultSymbol, useDefault);
		this.fieldName = fieldName;
		this.colorScheme = colorScheme;
		this.order = order;
	}

	public Symbolizer[] getSymbols() throws IOException {
		if (symbols == null) {
			Object[] values = getValues();
			symbols = new Symbolizer[values.length];
			for (int i = 0; i < values.length; i++) {
				symbols[i] = symbols().get(values[i]);
			}
		}
		return symbols;
	}

	public Object[] getValues() throws IOException {
		if (values == null) {
			values = symbols().keySet().toArray();
			Arrays.sort(values, order);
		}
		return values;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Color[] getColorScheme() {
		return colorScheme;
	}

	@Override
	protected Style createStyle() throws IOException {
		Style style = styleFactory.createStyle();
		List<FeatureTypeStyle> styles = style.featureTypeStyles();
		List<Filter> filters = new ArrayList<Filter>();
		for (int i = getValues().length - 1; i >= 0; i--) {
			Object value = getValues()[i];
			Filter filter = filterFactory.equals(
					filterFactory.property(fieldName),
					filterFactory.literal(value));
			filters.add(filter);
			FeatureTypeStyle fts = featureTypeStyle(rule(filter,
					symbols().get(value)));
			styles.add(fts);
		}

		addDefaultStyleIfNeeded(style, filters);

		return style;
	}

	private Map<Object, Symbolizer> symbols() throws IOException {
		if (symbolsMap == null) {
			this.symbolsMap = new HashMap<Object, Symbolizer>();

			Random rand = new Random(System.currentTimeMillis());
			SimpleFeatureIterator iterator = layer.getFeatureSource()
					.getFeatures().features();
			while (iterator.hasNext()) {
				Object value = iterator.next().getAttribute(fieldName);

				if (symbolsMap.get(value) == null) {
					Class<? extends Geometry> type = layer.getShapeType();
					Color color = colorScheme[rand.nextInt(colorScheme.length)];
					Symbolizer symbol = defaultSymbols.createDefaultSymbol(
							type, color, value.toString());
					symbolsMap.put(value, symbol);
				}
			}
		}
		return symbolsMap;
	}
}
