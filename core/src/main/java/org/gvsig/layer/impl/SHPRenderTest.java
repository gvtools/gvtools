package org.gvsig.layer.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.geotools.data.shapefile.indexed.IndexedShapefileDataStore;
import org.geotools.filter.FilterFactoryImpl;
import org.geotools.filter.identity.FeatureIdImpl;
import org.geotools.map.MapContent;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.StyleFactoryImpl;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.Identifier;

public class SHPRenderTest {

	static StyleFactory styleFactory = new StyleFactoryImpl();
	static FilterFactory2 filterFactory = new FilterFactoryImpl();

	public static void main(String[] args) throws Exception {
		// ~ 100.000 features
		IndexedShapefileDataStore ds = new IndexedShapefileDataStore(new URL(
				"file:///home/fergonco/carto/vias.shp"));

		// Default style
		Rule defaultRule = createRule(Color.BLUE, 1);
		Style defaultStyle = createStyle(new Rule[] { defaultRule });
		// defaultRule.setElseFilter(true);

		// Select some of the features
		Rule selectionRule = createRule(Color.YELLOW, 1);
		Set<Identifier> ids = new HashSet<Identifier>();
		for (int i = 0; i < 5000; i++) {
			ids.add(new FeatureIdImpl("vias." + i));
		}
		selectionRule.setFilter(filterFactory.id(ids));
		Style selectionStyle = createStyle(new Rule[] { selectionRule });

		// Create mapcontent
		MapContent mapContent = new MapContent();
		mapContent.addLayer(new org.geotools.map.FeatureLayer(ds
				.getFeatureSource(), defaultStyle));
		mapContent.addLayer(new org.geotools.map.FeatureLayer(ds
				.getFeatureSource(), selectionStyle));

		// Render
		GTRenderer renderer = new StreamingRenderer();
		renderer.setMapContent(mapContent);
		BufferedImage image = new BufferedImage(400, 400,
				BufferedImage.TYPE_INT_ARGB);
		Rectangle imageArea = new Rectangle(0, 0, image.getWidth(),
				image.getHeight());
		Graphics2D g = image.createGraphics();
		long t1 = System.currentTimeMillis();
		renderer.paint(g, imageArea, ds.getFeatureSource(ds.getTypeNames()[0])
				.getBounds());
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		g.dispose();
		ImageIO.write(image, "png", new File("/tmp/test.png"));
	}

	private static Style createStyle(Rule[] rules) {
		FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(rules);
		Style style = styleFactory.createStyle();
		style.featureTypeStyles().add(fts);
		return style;
	}

	private static Rule createRule(Color color, int width) {
		Stroke stroke = styleFactory.createStroke(filterFactory.literal(color),
				filterFactory.literal(width));

		LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

		Rule rule = styleFactory.createRule();
		rule.symbolizers().add(sym);
		return rule;
	}

}
