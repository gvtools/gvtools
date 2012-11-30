package com.iver.cit.gvsig.fmap;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * <p>
 * Event produced when the projection of the view port has changed.
 * </p>
 */
public class ProjectionEvent extends FMapEvent {
	/**
	 * <p>
	 * The new projection.
	 * </p>
	 */
	private CoordinateReferenceSystem newCrs;

	/**
	 * <p>
	 * Identifies this object as an event related with the projection.
	 * </p>
	 */
	private static final int PROJECTION_EVENT = 0;

	/**
	 * <p>
	 * Creates a new projection event.
	 * </p>
	 * 
	 * @param crs
	 *            the new projection
	 * 
	 * @return a new projection event
	 */
	public static ProjectionEvent createCrsEvent(CoordinateReferenceSystem crs) {
		return new ProjectionEvent(crs, PROJECTION_EVENT);
	}

	/**
	 * <p>
	 * Creates a new projection event of the specified type.
	 * </p>
	 * 
	 * @param crs
	 *            the new projection
	 * @param eventType
	 *            identifier of this kind of event
	 */
	private ProjectionEvent(CoordinateReferenceSystem crs, int eventType) {
		setEventType(eventType);
		newCrs = crs;
	}

	/**
	 * <p>
	 * Gets the new projection.
	 * </p>
	 * 
	 * @return the new projection
	 */
	public CoordinateReferenceSystem getNewCrs() {
		return newCrs;
	}
}
