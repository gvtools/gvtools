package org.gvsig.legend.impl;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.Interval;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.Literal;
import org.opengis.filter.expression.PropertyName;

public class ProportionalLegend extends AbstractLegend {
	private String valueField, normalizationField;
	private Interval size;
	private Symbolizer template, background;
	private Symbolizer[] symbols;

	private Expression sizeExp;

	ProportionalLegend() {
	}

	public void init(Layer layer, String valueField, String normalizationField,
			Symbolizer template, Symbolizer background, Interval size) {
		super.setLayer(layer);
		this.valueField = valueField;
		this.normalizationField = normalizationField;
		this.size = size;
		this.template = template;
		this.background = background;
	}

	public String getValueField() {
		return valueField;
	}

	public String getNormalizationField() {
		return normalizationField;
	}

	public Interval getSize() {
		return size;
	}

	public Symbolizer getTemplate() {
		return template;
	}

	public Symbolizer getBackground() {
		return background;
	}

	@Override
	protected Style createStyle() throws IOException {
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(featureTypeStyle(rule(symbols())));
		return style;
	}

	private Symbolizer[] symbols() throws IOException {
		if (symbols == null) {
			createSymbols();
			ResizeCopyStyleVisitor visitor = new ResizeCopyStyleVisitor(
					sizeExp, styleFactory, filterFactory);
			template.accept(visitor);
			Symbolizer symbol = (Symbolizer) visitor.getCopy();
			if (background != null) {
				symbols = new Symbolizer[] { background, symbol };
			} else {
				symbols = new Symbolizer[] { symbol };
			}
		}

		return symbols;

	}

	private void createSymbols() throws IOException {
		boolean useNormalization = normalizationField != null
				&& !normalizationField.equals(valueField);

		double maxValue = Double.NEGATIVE_INFINITY;
		double minValue = Double.MAX_VALUE;
		double valueRange = 0;

		if (!useNormalization) {
			SimpleFeatureIterator iterator = getLayer().getFeatureSource()
					.getFeatures().features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				double value = Double.parseDouble(feature.getAttribute(
						valueField).toString());
				if (value > maxValue) {
					maxValue = value;
				}
				if (value < minValue) {
					minValue = value;
				}
			}
			valueRange = maxValue - minValue;
		}

		Literal min = filterFactory.literal(size.getMin());
		Literal sizeRange = filterFactory
				.literal(size.getMax() - size.getMin());
		PropertyName value = filterFactory.property(valueField);
		PropertyName normalization = filterFactory.property(normalizationField);

		if (useNormalization) {
			// min + (value * range / normalization)
			Expression exp = filterFactory.divide(sizeRange, normalization);
			exp = filterFactory.multiply(value, exp);
			this.sizeExp = filterFactory.add(min, exp);
		} else {
			// min + ((value - min) * sizeRange / valueRange)
			Expression exp = filterFactory.subtract(value, min);
			exp = filterFactory.multiply(exp, sizeRange);
			exp = filterFactory.divide(exp, filterFactory.literal(valueRange));
			this.sizeExp = filterFactory.add(min, exp);
		}
	}
}
