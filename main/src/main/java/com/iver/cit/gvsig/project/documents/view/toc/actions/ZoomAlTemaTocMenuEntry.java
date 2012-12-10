package com.iver.cit.gvsig.project.documents.view.toc.actions;

import geomatico.events.EventBus;
import geomatico.events.ExceptionEvent;

import java.io.IOException;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.gvsig.inject.InjectorSingleton;
import org.gvsig.layer.Layer;
import org.gvsig.map.MapContext;
import org.gvsig.util.EnvelopeUtils;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.project.documents.view.toc.gui.ILayerAction;
import com.vividsolutions.jts.geom.Envelope;

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
/* CVS MESSAGES:
 *
 * $Id: ZoomAlTemaTocMenuEntry.java 13869 2007-09-19 16:00:32Z jaume $
 * $Log$
 * Revision 1.6  2007-09-19 15:52:16  jaume
 * ReadExpansionFileException removed from this context
 *
 * Revision 1.5  2007/03/06 16:37:08  caballero
 * Exceptions
 *
 * Revision 1.4  2007/01/04 07:24:31  caballero
 * isModified
 *
 * Revision 1.3  2006/10/18 16:01:13  sbayarri
 * Added zoomToExtent method to MapContext.
 *
 * Revision 1.2  2006/10/02 13:52:34  jaume
 * organize impots
 *
 * Revision 1.1  2006/09/15 10:41:30  caballero
 * extensibilidad de documentos
 *
 * Revision 1.1  2006/09/12 15:58:14  jorpiell
 * "Sacadas" las opcines del men� de FPopupMenu
 *
 *
 */
public class ZoomAlTemaTocMenuEntry extends AbstractLayerAction implements
		ILayerAction {
	public String getGroup() {
		return "group2"; // FIXME
	}

	public int getGroupOrder() {
		return 20;
	}

	public int getOrder() {
		return 1;
	}

	public String getText() {
		return PluginServices.getText(this, "Zoom_a_la_capa");
	}

	public boolean isEnabled(MapContext mapContext) {
		return true;
	}

	public boolean isVisible(MapContext mapContext) {
		return getSelected(mapContext).length > 0;
	}

	public void execute(MapContext mapContext, MapControl mapControl) {

		// 050209, jmorell: Para que haga un zoom a un grupo de capas
		// seleccionadas.

		Layer[] selected = getSelected(mapContext);
		Envelope selectedEnvelope = null;
		for (Layer layer : selected) {
			try {
				ReferencedEnvelope envelope = layer.getBounds();
				if (selectedEnvelope == null) {
					selectedEnvelope = new Envelope(envelope);
				} else {
					selectedEnvelope.expandToInclude(envelope);
				}
			} catch (IOException e) {
				EventBus eventBus = InjectorSingleton.getInjector()
						.getInstance(EventBus.class);
				eventBus.fireEvent(new ExceptionEvent(
						"Cannot get the bounds of layer: " + layer.getName(), e));
			}
		}
		mapControl.getViewPort().setExtent(
				EnvelopeUtils.toRectangle2D(selectedEnvelope));
	}

	@Override
	public String getDescription() {
		return PluginServices.getText(this, "zoom_to_layer_tooltip");
	}
}
