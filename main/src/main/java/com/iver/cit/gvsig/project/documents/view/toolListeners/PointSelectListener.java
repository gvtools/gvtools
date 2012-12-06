/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
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
 *   Av. Blasco Ibáñez, 50
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

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.fmap.tools.BehaviorException;
import com.iver.cit.gvsig.fmap.tools.PointSelectionListener;
import com.iver.cit.gvsig.fmap.tools.Events.PointEvent;

/**
 * <p>
 * Inherits {@link PointSelectListener PointSelectListener} enabling/disabling
 * special controls for managing the data selected.
 * </p>
 * 
 * @see PointSelectionListener
 * 
 * @author Vicente Caballero Navarro
 */
public class PointSelectListener extends PointSelectionListener {
	/**
	 * <p>
	 * Creates a new <code>PointSelectListener</code> object.
	 * </p>
	 * 
	 * @param mapCtrl
	 *            the <code>MapControl</code> where will be applied the changes
	 */
	public PointSelectListener(MapControl mapCtrl) {
		super(mapCtrl);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.PointSelectionListener#point(com.iver.cit
	 * .gvsig.fmap.tools.Events.PointEvent)
	 */
	public void point(PointEvent event) throws BehaviorException {
		super.point(event);
		PluginServices.getMainFrame().enableControls();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.iver.cit.gvsig.fmap.tools.PointSelectionListener#pointDoubleClick
	 * (com.iver.cit.gvsig.fmap.tools.Events.PointEvent)
	 */
	public void pointDoubleClick(PointEvent event) throws BehaviorException {
		/*
		 * try { FLayer[] actives = mapCtrl.getMapContext()
		 * .getLayers().getActives(); for (int i=0; i < actives.length; i++){ if
		 * (actives[i] instanceof FLyrAnnotation && actives[i].isEditing()) {
		 * FLyrAnnotation lyrAnnotation = (FLyrAnnotation) actives[i];
		 * 
		 * lyrAnnotation.setSelectedEditing();
		 * lyrAnnotation.setInEdition(lyrAnnotation
		 * .getRecordset().getSelection().nextSetBit(0)); FLabel
		 * fl=lyrAnnotation.getLabel(lyrAnnotation.getInEdition()); if
		 * (fl!=null){
		 * 
		 * View vista=(View)PluginServices.getMDIManager().getActiveView();
		 * TextFieldEdit tfe=new TextFieldEdit(lyrAnnotation);
		 * 
		 * tfe.show(vista.getMapControl().getViewPort().fromMapPoint(fl.getOrig()
		 * ),vista.getMapControl()); }
		 * 
		 * 
		 * } }
		 * 
		 * } catch (DriverLoadException e) { e.printStackTrace(); throw new
		 * BehaviorException("Fallo con el recordset"); } catch
		 * (com.iver.cit.gvsig.fmap.DriverException e) { e.printStackTrace();
		 * throw new BehaviorException("Fallo con el recordset"); }
		 */
	}
}
