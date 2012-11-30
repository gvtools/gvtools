package org.gvsig.util;

import java.awt.geom.Rectangle2D;

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
}
