package org.gvsig.util;

import java.awt.geom.Rectangle2D;

import org.gvsig.persistence.generated.ExtentType;

import com.vividsolutions.jts.geom.Envelope;

public class EnvelopeUtils {

	public static Rectangle2D toRectangle2D(Envelope envelope) {
		return new Rectangle2D.Double(envelope.getMinX(), envelope.getMinY(),
				envelope.getWidth(), envelope.getHeight());
	}

	public static Envelope toEnvelope(Rectangle2D rect) {
		return new Envelope(rect.getMinX(), rect.getMaxX(), rect.getMinY(),
				rect.getMaxY());
	}

	public static ExtentType toXML(Rectangle2D extent) {
		ExtentType xml = new ExtentType();
		xml.setMinx(extent.getMinX());
		xml.setMiny(extent.getMinY());
		xml.setMaxx(extent.getMaxX());
		xml.setMaxy(extent.getMaxY());

		return xml;
	}

	public static Rectangle2D fromXML(ExtentType xml) {
		Rectangle2D ret = new Rectangle2D.Double();
		ret.setRect(xml.getMinx(), xml.getMiny(),
				xml.getMaxx() - xml.getMinx(), xml.getMaxy() - xml.getMiny());
		return ret;
	}
}
