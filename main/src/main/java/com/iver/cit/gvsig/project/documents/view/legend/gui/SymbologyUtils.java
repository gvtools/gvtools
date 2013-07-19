package com.iver.cit.gvsig.project.documents.view.legend.gui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.apache.log4j.Logger;
import org.geotools.styling.Symbolizer;

import com.iver.utiles.FileUtils;
import com.iver.utiles.IPersistence;
import com.iver.utiles.NotExistInXMLEntity;
import com.iver.utiles.XMLEntity;

public class SymbologyUtils {
	private static final Logger logger = Logger.getLogger(SymbologyUtils.class);

	public static final String FactorySymbolLibraryPath = FileUtils
			.getAppHomeDir() + "Symbols";

	public static String SymbolLibraryPath = FactorySymbolLibraryPath;

	public static void drawInsideRectangle(Symbolizer symbolizer, Graphics2D g,
			Rectangle rectangle) {
		// TODO gtintegration: implement
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
}
