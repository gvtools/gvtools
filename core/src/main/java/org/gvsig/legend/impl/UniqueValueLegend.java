package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.geotools.data.simple.SimpleFeatureIterator;
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
	private Object[] orderedKeys;
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
		Object[] values = getValues();
		Symbolizer[] ret = new Symbolizer[values.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = symbols().get(values[i]);
		}
		return ret;
	}

	public Object[] getValues() throws IOException {
		return getSymbolizerKeys();
	}

	public String getFieldName() {
		return fieldName;
	}

	@Override
	protected Symbolizer[] getSymbols(Object value) {
		return new Symbolizer[] { symbolsMap.get(value) };
	}

	@Override
	protected Object[] getSymbolizerKeys() throws IOException {
		return getOrderedKeys(symbols());
	}

	private Object[] getOrderedKeys(Map<Object, Symbolizer> symbolsMap) {
		if (orderedKeys == null) {
			orderedKeys = symbolsMap.keySet().toArray();
			Arrays.sort(orderedKeys, order);
		}
		return orderedKeys;
	}

	@Override
	protected Filter getFilter(Object key) {
		return filterFactory.equals(filterFactory.property(fieldName),
				filterFactory.literal(key));
	}

	public Color[] getColorScheme() {
		return colorScheme;
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
