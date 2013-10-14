package org.gvsig.legend.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.TransformerException;

import org.geotools.data.Base64;
import org.geotools.styling.FeatureTypeConstraint;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Font;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.SLDParser;
import org.geotools.styling.SLDTransformer;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyledLayerDescriptor;
import org.geotools.styling.Symbolizer;
import org.geotools.styling.TextSymbolizer;
import org.geotools.styling.UserLayer;
import org.gvsig.layer.Layer;
import org.gvsig.legend.DefaultSymbols;
import org.gvsig.legend.Legend;
import org.gvsig.persistence.PersistenceException;
import org.gvsig.persistence.generated.LegendType;
import org.gvsig.persistence.generated.StringPropertyType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.Literal;
import org.opengis.filter.expression.PropertyName;

public abstract class AbstractLegend implements Legend {
	private Style style;

	private Layer layer;

	private TextSymbolizer labeling;

	@Inject
	protected StyleFactory styleFactory;
	@Inject
	protected FilterFactory2 filterFactory;
	@Inject
	protected DefaultSymbols defaultSymbols;

	@Override
	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	protected Layer getLayer() {
		return layer;
	}

	@Override
	public final Style getStyle() throws IOException {
		if (layer == null) {
			throw new IllegalStateException("bug! You must initialize "
					+ "the legend first!");
		}

		if (style == null) {
			style = createStyle();

			if (labeling != null) {
				style.featureTypeStyles().add(featureTypeStyle(rule(labeling)));
			}
		}
		return style;
	}

	@Override
	public TextSymbolizer getLabeling() {
		return labeling;
	}

	@Override
	public void setLabeling(TextSymbolizer labeling) {
		this.labeling = labeling;
	}

	protected abstract Style createStyle() throws IOException;

	protected FeatureTypeStyle featureTypeStyle(Rule... rules) {
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle();
		for (Rule rule : rules) {
			fts.rules().add(rule);
		}
		return fts;
	}

	protected Rule rule(Symbolizer... symbols) {
		Rule rule = createRule(symbols);
		rule.setElseFilter(true);
		return rule;
	}

	protected Rule rule(Filter filter, Symbolizer... symbols) {
		Rule rule = createRule(symbols);
		rule.setElseFilter(false);
		rule.setFilter(filter);
		return rule;
	}

	private Rule createRule(Symbolizer... symbols) {
		Rule rule = styleFactory.createRule();
		for (Symbolizer symbol : symbols) {
			rule.symbolizers().add(symbol);
		}
		return rule;
	}

	@Override
	public LegendType getXML() throws PersistenceException {
		LegendType type = new LegendType();
		if (labeling != null) {
			List<StringPropertyType> props = type.getProperties();

			Font font = labeling.getFont();
			String italic = font.getStyle().toString();
			String bold = font.getWeight().toString();
			String family = font.getFamily().get(0).toString();
			String field = labeling.getLabel().toString();

			Expression sizeExp = font.getSize();
			if (sizeExp instanceof Literal) {
				Integer size = sizeExp.evaluate(null, Integer.class);
				if (size != null) {
					props.add(property("label-size", size.toString()));
				}
			} else if (sizeExp instanceof PropertyName) {
				String sizeField = ((PropertyName) sizeExp).getPropertyName();
				props.add(property("label-size-field", sizeField));
			}

			props.add(property("label-family", family));
			props.add(property("label-italic", italic));
			props.add(property("label-bold", bold));
			props.add(property("label-field", field));

			Fill fill = labeling.getFill();
			Color color = SLD.color(fill);
			double opacity = SLD.opacity(fill);
			props.add(property("label-color", Integer.toString(color.getRGB())));
			props.add(property("label-opacity", Double.toString(opacity)));

		}
		return type;
	}

	public void setXML(LegendType xml) {
		String family = null;
		String italic = Font.Style.NORMAL;
		String bold = Font.Weight.NORMAL;
		String field = null;
		double opacity = 1;
		Color color = Color.black;
		Expression size = null;

		for (StringPropertyType property : xml.getProperties()) {
			String name = property.getPropertyName();
			String value = property.getPropertyValue();

			if (name.equals("label-family")) {
				family = value;
			} else if (name.equals("label-italic")) {
				italic = value;
			} else if (name.equals("label-bold")) {
				bold = value;
			} else if (name.equals("label-field")) {
				field = value;
			} else if (name.equals("label-size")) {
				size = filterFactory.literal(Integer.parseInt(value));
			} else if (name.equals("label-size-field")) {
				size = filterFactory.property(value);
			} else if (name.equals("label-color")) {
				color = new Color(Integer.parseInt(value));
			} else if (name.equals("label-opacity")) {
				opacity = Double.parseDouble(value);
			}
		}

		if (family != null && field != null && size != null) {
			Font font = new StyleBuilder(filterFactory).createFont(
					filterFactory.literal(family),
					filterFactory.literal(italic), filterFactory.literal(bold),
					size);
			labeling = styleFactory.createTextSymbolizer();
			labeling.setFill(styleFactory.createFill(
					filterFactory.literal(color),
					filterFactory.literal(opacity)));
			labeling.setFont(font);
			labeling.setLabel(filterFactory.property(field));
		}
	}

	protected String encode(Symbolizer symbol) throws TransformerException {
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(featureTypeStyle(rule(symbol)));

		StyledLayerDescriptor sld = styleFactory.createStyledLayerDescriptor();
		UserLayer layer = styleFactory.createUserLayer();
		layer.setLayerFeatureConstraints(new FeatureTypeConstraint[] { null });
		sld.addStyledLayer(layer);
		layer.addUserStyle(style);

		String xml = new SLDTransformer().transform(sld);
		return Base64.encodeBytes(xml.getBytes());
	}

	protected Symbolizer decode(String string) {
		InputStreamReader reader = new InputStreamReader(
				new ByteArrayInputStream(Base64.decode(string)));
		SLDParser parser = new SLDParser(styleFactory, reader);
		Style[] styles = parser.readXML();
		return styles[0].featureTypeStyles().get(0).rules().get(0)
				.symbolizers().get(0);
	}

	protected StringPropertyType property(String name, String value) {
		StringPropertyType property = new StringPropertyType();
		property.setPropertyName(name);
		property.setPropertyValue(value);
		return property;
	}

	protected StringPropertyType symbol(String name, Symbolizer symbol)
			throws PersistenceException {
		try {
			return property(name, encode(symbol));
		} catch (TransformerException e) {
			throw new PersistenceException("Cannot encode symbolizer", e);
		}
	}
}
