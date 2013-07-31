package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.measure.quantity.Length;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.apache.log4j.Logger;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.LiteShape2;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.StyledShapePainter;
import org.geotools.renderer.style.SLDStyleFactory;
import org.geotools.renderer.style.Style2D;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Symbolizer;
import org.geotools.util.NumberRange;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.iver.utiles.FileUtils;
import com.iver.utiles.IPersistence;
import com.iver.utiles.NotExistInXMLEntity;
import com.iver.utiles.XMLEntity;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class SymbologyUtils {
	private static final Logger logger = Logger.getLogger(SymbologyUtils.class);

	public static final String FactorySymbolLibraryPath = FileUtils
			.getAppHomeDir() + "Symbols";
	public static String SymbolLibraryPath = FactorySymbolLibraryPath;

	private static final GeometryFactory gf = new GeometryFactory();

	public static void drawInsideRectangle(Symbolizer symbolizer, Graphics2D g,
			Rectangle r) {
		Class<?> geomBinding = Point.class;
		Geometry geom;
		g.setColor(Color.white);
		g.fillRect(r.x, r.y, r.width, r.height);

		if (symbolizer instanceof PointSymbolizer) {
			geomBinding = Point.class;
			geom = gf
					.createPoint(new Coordinate(r.getCenterX(), r.getCenterY()));
		} else if (symbolizer instanceof LineSymbolizer) {
			geomBinding = LineString.class;
			Coordinate begin = new Coordinate(r.getWidth() / 4, r.getCenterY());
			Coordinate end = new Coordinate(r.getWidth() * 3 / 4,
					r.getCenterY());
			geom = gf.createLineString(new Coordinate[] { begin, end });
		} else if (symbolizer instanceof PolygonSymbolizer) {
			geomBinding = Polygon.class;

			double minx = r.getWidth() / 4;
			double maxx = r.getWidth() * 3 / 4;
			double miny = r.getHeight() / 4;
			double maxy = r.getHeight() * 3 / 4;
			LinearRing shell = gf.createLinearRing(new Coordinate[] {
					new Coordinate(minx, miny), new Coordinate(minx, maxy),
					new Coordinate(maxx, maxy), new Coordinate(maxx, miny),
					new Coordinate(minx, miny) });
			geom = gf.createPolygon(shell, null);
		} else {
			throw new RuntimeException("Unsupported symbolizer class: "
					+ symbolizer.getClass().getCanonicalName());
		}

		// Build type
		SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
		typeBuilder.setName("foo");
		typeBuilder.setCRS(DefaultGeographicCRS.WGS84);
		typeBuilder.add("geom", geomBinding);
		SimpleFeatureType type = typeBuilder.buildFeatureType();

		// Build shape
		LiteShape2 sample;
		try {
			sample = new LiteShape2(geom, null, null, false);
		} catch (Exception e) {
			logger.error("Cannot draw symbol preview", e);
			return;
		}

		// Build feature
		Feature feature = new SimpleFeatureBuilder(type).buildFeature("id");

		// Create style and paint
		Style2D style = new SLDStyleFactory().createStyle(feature, symbolizer,
				NumberRange.create(0, 1));
		new StyledShapePainter().paint(g, sample, style, 1);
	}

	/**
	 * Factory that allows to create <b>ISymbol</b>'s from an ISymbol xml
	 * descriptor. A barely specific XMLEntity object. The string passed in the
	 * second argument is the description text that will be used in case no
	 * description is supplied by the symbol's xml descriptor.
	 * 
	 * @param xml
	 *            , the symbol's xml descriptor
	 * @param defaultDescription
	 *            , a human readable description string for the symbol.
	 * @return ISymbol
	 */
	public static Symbolizer createSymbolFromXML(XMLEntity xml,
			String defaultDescription) {
		if (!xml.contains("desc")) {
			if (defaultDescription == null)
				defaultDescription = "";
			xml.putProperty("desc", defaultDescription);
		}
		return (Symbolizer) createFromXML(xml);
	}

	/**
	 * Creates an <b>Object</b> described by the <b>XMLEntity</b> xml, please
	 * reffer to the XMLEntity definition contract to know what is the format of
	 * the xml argument. The result of this method is an <b>Object</b> that you
	 * can cast to the type you were looking for by means of the xml entity.
	 * 
	 * @param xml
	 * @return Object
	 */
	private static Object createFromXML(XMLEntity xml) {
		String className = null;
		try {
			className = xml.getStringProperty("className");
		} catch (NotExistInXMLEntity e) {
			logger.error("Class name not set.\n"
					+ " Maybe you forgot to add the"
					+ " putProperty(\"className\", yourClassName)"
					+ " call in the getXMLEntity method of your class", e);
		}

		Class clazz = null;
		IPersistence obj = null;
		String s = className;

		try {
			clazz = Class.forName(className);

			if (xml.contains("desc")) {
				s += " \"" + xml.getStringProperty("desc") + "\"";
			}
			// TODO: Modify the patch the day we deprecate FSymbol
			// begin patch
			// end patch

			obj = (IPersistence) clazz.newInstance();
			// logger.info(Messages.getString("creating")+"....... "+s);
			try {
				obj.setXMLEntity(xml);
			} catch (NotExistInXMLEntity neiXML) {
				logger.error("failed_creating_object" + ": " + s);
				throw neiXML;
			}
		} catch (InstantiationException e) {
			logger.error("Trying to instantiate an interface"
					+ " or abstract class + " + className, e);
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException: does your class have an"
					+ " anonymous constructor?", e);
		} catch (ClassNotFoundException e) {
			logger.error("No class called " + className
					+ " was found.\nCheck the following.\n<br>"
					+ "\t- The fullname of the class you're looking "
					+ "for matches the value in the className "
					+ "property of the XMLEntity (" + className + ").\n<br>"
					+ "\t- The jar file containing your symbol class is in"
					+ "the application classpath<br>", e);
		}
		return obj;
	}

	public static XMLEntity getXMLEntity(Symbolizer sym) {
		// TODO gtintegration
		return null;
	}

	public static Symbolizer clone(Symbolizer symbolizer) {
		// TODO gtintegration
		return symbolizer;
	}

	public static org.gvsig.units.Unit convert2gvsigUnits(Unit<Length> unit) {
		if (unit == null) {
			return null;
		} else if (unit.equals(SI.MILLIMETER)) {
			return org.gvsig.units.Unit.MILIMETERS;
		} else if (unit.equals(SI.CENTIMETER)) {
			return org.gvsig.units.Unit.CENTIMETERS;
		} else if (unit.equals(SI.METER)) {
			return org.gvsig.units.Unit.METERS;
		} else if (unit.equals(SI.KILOMETER)) {
			return org.gvsig.units.Unit.KILOMETERS;
		} else {
			return null;
		}
	}

	public static Unit<Length> convert2JavaUnits(org.gvsig.units.Unit unit) {
		if (unit == null) {
			return null;
		}
		switch (unit) {
		case CENTIMETERS:
			return SI.CENTIMETER;
		case DEGREES:
			// TODO implement
			return null;
		case FEET:
			// TODO implement
			return null;
		case INCHES:
			// TODO implement
			return null;
		case KILOMETERS:
			return SI.KILOMETER;
		case METERS:
			return SI.METER;
		case MILES:
			// TODO implement
			return null;
		case MILIMETERS:
			return SI.MILLIMETER;
		case YARDS:
			// TODO implement
			return null;
		default:
			return null;
		}
	}
}
