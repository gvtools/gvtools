/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2004 IVER T.I. and Generalitat Valenciana.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,USA.
 *
 * For more information, contact:
 *
 *  Generalitat Valenciana
 *   Conselleria d'Infraestructures i Transport
 *   Av. Blasco Ib��ez, 50
 *   46010 VALENCIA
 *   SPAIN
 *
 *      +34 963862235
 *   gvsig@gva.es
 *      www.gvsig.gva.es
 *
 *    or
 *
 *   IVER T.I. S.A
 *   Salamanca 50
 *   46005 Valencia
 *   Spain
 *
 *   +34 963163400
 *   dac@iver.es
 */
package com.iver.cit.gvsig.project.documents.view.toolListeners;

import geomatico.events.EventBus;
import geomatico.events.ExceptionEvent;

import java.awt.Cursor;
import java.awt.geom.Point2D;
import java.text.NumberFormat;

import javax.inject.Inject;

import org.geotools.referencing.CRS;
import org.gvsig.crs.ExtendedMathTransform;
import org.gvsig.units.Unit;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiFrame.MainFrame;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;
import com.iver.cit.gvsig.fmap.tools.Listeners.PointListener;

/**
 * <p>
 * Listener that displays at the status bar of the application's main frame, the
 * value of the point coordinates of the mouse's cursor on the associated
 * <code>MapControl</code>, just as is received a <code>PointEvent</code> event.
 * </p>
 * 
 * <p>
 * Calculates the coordinates equivalent to the point according this rules:
 * <ul>
 * <li>uses <i><code>formatDegrees(p.get{X or Y}()</code></i> if the associated
 * <code>MapControl</code> object isn't projected.</li>
 * <li>uses <i>
 * <code>formatDegrees({MapControl's projection}.toGeo(p.get{X or Y}())</code>
 * </i> if the associated <code>MapControl</code> object is projected and its
 * <code>ViewPort</code>'s distance units are in degrees.</li>
 * <li>uses <i>
 * <code>{NumberFormat according to {@link #setFractionDigits(Point2D) #setFractionDigits(Point2D)}}.format((p.get{X or Y}()/MapContext.CHANGEM[mapControl.getViewPort().getDistanceUnits()])*MapContext.CHANGEM[mapControl.getViewPort().getMapUnits()])</code>
 * </i> otherwise.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The <u>prefix</u> of the coordinate expressions will be:
 * <ul>
 * <li>Longitude "<i>Long =</i>" and latitude "<i>Lat =</i>", if the associated
 * <i>MapControl</i> object isn't projected, or the current distance unit of the
 * <code>MapControl</code>'s view port is in degrees.</li>
 * <li>X "<i>X =</i>" and Y "<i>Y =</i>", otherwise.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * And the <u>sufix</u> value:
 * <ul>
 * <li>If the associated <i>MapControl</i> object isn't projected, or the
 * current distance unit of the <code>MapControl</code>'s view port is in
 * degrees(expected latitude or longitude), according this pattern:<br>
 * <code><b><i>S?G� M' S''</i></b></code>, having:<br>
 * <ul>
 * <li><i>S?</i> : optionally, if the value is negative, sets a "-" symbol.</li>
 * <li><i>G</i> : equivalent grades.</li>
 * <li><i>M</i> : equivalent minutes.</li>
 * <li><i>S</i> : equivalent seconds.</li>
 * </ul>
 * </li>
 * <li>Otherwise a decimal number according this rules:
 * <ul>
 * <li><i>8 decimals</i>, if is using any of the following geographic coordinate
 * systems:
 * <ul>
 * <li><i>EPSG:4230 (known as <a
 * href="http://en.wikipedia.org/wiki/ED50">ED50</a>)</i>.</li>
 * <li><i>EPSG:4326 (known as <a
 * href="http://en.wikipedia.org/wiki/WGS84">WGS84</a>)</i>.</li>
 * </ul>
 * <li><i>2 decimals</i>, otherwise.</li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * 
 * @author Vicente Caballero Navarro
 */
public class StatusBarListener implements PointListener {
	/**
	 * Reference to the <code>MapControl</code> object that uses.
	 */
	private MapControl mapControl = null;

	/**
	 * Format of the coordinates. Is used to set the number of decimals.
	 */
	private NumberFormat nf = null;

	@Inject
	private EventBus eventBus;

	/**
	 * <p>
	 * Creates a new <code>StatusBarListener</code> object.
	 * </p>
	 * 
	 * @param mc
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public StatusBarListener(MapControl mc) {
		mapControl = mc;
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener#getCursor()
	 */
	public Cursor getCursor() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.cit.gvsig.fmap.tools.Listeners.ToolListener#cancelDrawing()
	 */
	public boolean cancelDrawing() {
		return false;
	}

	/*
	 * 050211, jmorell: M�todo modificado para mejorar la manera de mostrar las
	 * coordenadas geod�sicas en la barra de estado. Muestra Lat y Lon y aumenta
	 * el n�mero de decimales para cuando trabajemos en coordenadas geod�sicas.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#point(com.iver.
	 * cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point(PointEvent event) throws BehaviorException {
		String[] axisText = new String[2];
		axisText[0] = "X = ";
		axisText[1] = "Y = ";
		Point2D p = mapControl.getViewPort().toMapPoint(event.getPoint());
		setFractionDigits(p);
		axisText = setCoorDisplayText(axisText);
		MainFrame mF = PluginServices.getMainFrame();

		if (mF != null) {

			mF.getStatusBar().setMessage(
					"units",
					PluginServices.getText(this, mapControl.getMapContext()
							.getDistanceUnits().name));
			// FJP: No se debe llamar a setControlValue desde aqu�, porque
			// cambia la escala, y con ella el viewPort (adem�s, de
			// la vista que no es).
			// mF.getStatusBar().setControlValue("scale",String.valueOf(mapControl.getMapContext().getScaleView()));
			// Fin
			mF.getStatusBar().setMessage("projection",
					CRS.toSRS(mapControl.getMapContext().getCRS()));

			try {
				String[] coords = getCoords(p);
				mF.getStatusBar().setMessage("x", axisText[0] + coords[0]);
				mF.getStatusBar().setMessage("y", axisText[1] + coords[1]);
			} catch (FactoryException e) {
				eventBus.fireEvent(new ExceptionEvent(
						ExceptionEvent.Severity.WARNING,
						"Cannot transform the coordinate", e));
			} catch (TransformException e) {
				eventBus.fireEvent(new ExceptionEvent(
						ExceptionEvent.Severity.WARNING,
						"Cannot transform the coordinate", e));
			}
		}
	}

	/**
	 * <p>
	 * Sets the number of decimals of the coordinates that will be displayed,
	 * according the current projection of the associated
	 * <code>MapControl</code>:
	 * <ul>
	 * <li><i>8 decimals</i>, if is using geographical coordinates:
	 * <ul>
	 * <li><i>EPSG:4230 (known as <a
	 * href="http://en.wikipedia.org/wiki/ED50">ED50</a>)</i>.</li>
	 * <li><i>EPSG:4326 (known as <a
	 * href="http://en.wikipedia.org/wiki/WGS84">WGS84</a>)</i>.</li>
	 * </ul>
	 * <li><i>2 decimals</i>, otherwise.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param p
	 *            unused parameter
	 * 
	 * @version 050211
	 * @author jmorell.
	 */
	public void setFractionDigits(Point2D p) {
		CoordinateReferenceSystem crs = mapControl.getMapContext().getCRS();
		if (!(crs instanceof ProjectedCRS)) {
			nf.setMaximumFractionDigits(8);
		} else {
			nf.setMaximumFractionDigits(2);
		}
	}

	/**
	 * <p>
	 * Gets the name of the coordinates:
	 * <ul>
	 * <li><i>Longitude</i> and <i>Latitude</i>, if the associated
	 * <i>MapControl</i> object isn't projected, or the current distance unit of
	 * the <code>MapControl</code>'s view port is in degrees.</li>
	 * <li><i>X</i> and <i>Y</i>, otherwise.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param p
	 *            array of at least two <code>String</code>, where text will be
	 *            stored and returned
	 * 
	 * @return text describing the coordinate value:
	 *         <ul>
	 *         <li>If isn't projected:
	 *         <ul>
	 *         <li><code>String[0]</code> : "Long = "</li>
	 *         <li><code>String[1]</code> : "Lat = "</li>
	 *         </ul>
	 *         </li>
	 *         <li>Otherwise:
	 *         <ul>
	 *         <li><code>String[0]</code> : "X = "</li>
	 *         <li><code>String[1]</code> : "Y = "</li>
	 *         </ul>
	 *         </li>
	 *         </ul>
	 * 
	 * @version 050211
	 * @author jmorell
	 */
	public String[] setCoorDisplayText(String[] axisText) {
		CoordinateReferenceSystem crs = mapControl.getMapContext().getCRS();
		if (!(crs instanceof ProjectedCRS)
				|| mapControl.getMapContext().getDistanceUnits().name
						.equals("Grados")) {
			axisText[0] = "Lon = ";
			axisText[1] = "Lat = ";
		} else {
			axisText[0] = "X = ";
			axisText[1] = "Y = ";
		}
		return axisText;
	}

	/**
	 * <p>
	 * Converts a decimal value (expected latitude or longitude) in degrees, and
	 * formats it according this pattern:<br>
	 * <code><b><i>S?G� M' S''</i></b></code>, having:<br>
	 * <ul>
	 * <li><i>S?</i> : optionally, if the value is negative, sets a "-" symbol.</li>
	 * <li><i>G</i> : equivalent grades.</li>
	 * <li><i>M</i> : equivalent minutes.</li>
	 * <li><i>S</i> : equivalent seconds.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param d
	 *            the latitude or longitude value to convert
	 * 
	 * @return value formatted in degrees
	 */
	private String formatDegrees(double d) {
		String signo = d < 0 ? "-" : "";
		d = Math.abs(d);
		long grado = 0;
		double minuto = 0;
		double segundo = 0;

		grado = (long) (d);
		minuto = (d - grado) * 60;
		segundo = (minuto - (long) minuto) * 60;
		// System.out.println("Grados: " + grado);
		// System.out.println("Minutos: " + minuto);
		// System.out.println("Segundos: " + segundo);
		return signo + grado + "� " + (long) minuto + "' " + (long) segundo
				+ "''";
	}

	/**
	 * <p>
	 * Returns the coordinates equivalent to <code>p</code>:
	 * <ul>
	 * <li>Uses <i><code>formatDegrees(p.get{X or Y}()</code></i> if the
	 * associated <code>MapControl</code> object isn't projected.</li>
	 * <li>Uses <i>
	 * <code>formatDegrees({MapControl's projection}.toGeo(p.get{X or Y}())</code>
	 * </i> if the associated <code>MapControl</code> object is projected and
	 * its <code>ViewPort</code>'s distance units are in degrees.</li>
	 * <li>Uses <i>
	 * <code>{NumberFormat according to {@link #setFractionDigits(Point2D) #setFractionDigits(Point2D)}}.format((p.get{X or Y}()/MapContext.CHANGEM[mapControl.getViewPort().getDistanceUnits()])*MapContext.CHANGEM[mapControl.getViewPort().getMapUnits()])</code>
	 * </i> otherwise.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param p
	 *            point 2D to convert in text coordinates according the
	 *            projection of the associated <code>MapControl</code> and the
	 *            distance units of its <code>ViewPort</code>.
	 * 
	 * @return coordinates equivalent to <code>p</code>, according to the
	 *         algorithm explained up
	 * @throws FactoryException
	 * @throws TransformException
	 */
	public String[] getCoords(Point2D p) throws FactoryException,
			TransformException {
		String[] coords = new String[2];
		CoordinateReferenceSystem crs = mapControl.getMapContext().getCRS();
		if (!(crs instanceof ProjectedCRS)) {
			coords[0] = String.valueOf(formatDegrees(p.getX()));
			coords[1] = String.valueOf(formatDegrees(p.getY()));
		} else {
			ProjectedCRS projected = (ProjectedCRS) crs;
			if (PluginServices.getText(this,
					mapControl.getMapContext().getDistanceUnits().name).equals(
					PluginServices.getText(this, "Grados"))) {
				MathTransform trans = CRS.findMathTransform(projected,
						projected.getBaseCRS());
				Point2D pgeo = new ExtendedMathTransform(trans).transform(p);
				coords[0] = String.valueOf(formatDegrees(pgeo.getX()));
				coords[1] = String.valueOf(formatDegrees(pgeo.getY()));
			} else {
				if (PluginServices.getText(this,
						mapControl.getMapContext().getMapUnits().name).equals(
						PluginServices.getText(this, "Grados"))) {
					mapControl.getViewPort().setMapUnits(Unit.METERS);
				}

				coords[0] = String.valueOf(nf.format((p.getX() / mapControl
						.getMapContext().getDistanceUnits().toMeter())
						* mapControl.getMapContext().getMapUnits().toMeter()));
				coords[1] = String.valueOf(nf.format((p.getY() / mapControl
						.getMapContext().getDistanceUnits().toMeter())
						* mapControl.getMapContext().getMapUnits().toMeter()));
			}
		}
		return coords;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.Listeners.PointListener#pointDoubleClick
	 * (com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void pointDoubleClick(PointEvent event) throws BehaviorException {
		// TODO Auto-generated method stub
	}
}
