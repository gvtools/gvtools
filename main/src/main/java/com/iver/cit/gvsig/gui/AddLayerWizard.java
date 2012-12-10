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
package com.iver.cit.gvsig.gui;

import geomatico.events.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.gvsig.events.LayerCreationErrorEvent;
import org.gvsig.layer.Layer;

import com.iver.cit.gvsig.fmap.MapControl;
import com.iver.cit.gvsig.gui.wizards.WizardListener;

public abstract class AddLayerWizard extends JPanel {
	private String tabName = "TabName";
	private MapControl mapCtrl = null;
	private List<WizardListener> listeners = new ArrayList<WizardListener>();

	public void addWizardListener(WizardListener listener) {
		listeners.add(listener);
	}

	public void callError(Exception e) {
		for (WizardListener listener : listeners) {
			listener.error(e);
		}
	}

	public void removeWizardListener(WizardListener listener) {
		listeners.remove(listener);
	}

	public void callStateChanged(boolean finishable) {
		for (WizardListener listener : listeners) {
			listener.wizardStateChanged(finishable);
		}
	}

	protected void setTabName(String name) {
		tabName = name;
	}

	public String getTabName() {
		return tabName;
	}

	abstract public void initWizard();

	/**
	 * Executes the wizard panel. If any layer contains errors or is not valid
	 * after execution, the implementation must fire an
	 * {@link LayerCreationErrorEvent} event to the {@link EventBus}.
	 */
	abstract public void execute();

	/**
	 * Gets the valid layers created by this panel.
	 * 
	 * @return the valid (and only the valid) layers created by this panel or an
	 *         empty array if none. It may never return <code>null</code>.
	 * @throws IllegalStateException
	 *             if this method is called before
	 *             {@link AddLayerWizard #execute()}
	 */
	abstract public Layer[] getLayers() throws IllegalStateException;

	/**
	 * You can use it to extract information from the mapControl that will
	 * receive the new layer. For example, projection to use, or visible extent.
	 * 
	 * @return Returns the mapCtrl.
	 */
	public MapControl getMapCtrl() {
		return mapCtrl;
	}

	/**
	 * @param mapCtrl
	 *            The mapCtrl to set.
	 */
	public void setMapCtrl(MapControl mapCtrl) {
		this.mapCtrl = mapCtrl;
	}

	/**
	 * 
	 * @return Messages describing why layers don't have good settings or null
	 *         if layers are ok. Returns null by default since it is a new
	 *         method not used until now. GeoDB wizard is the first to use this.
	 * 
	 */
	public String[] validateLayerSettings() {
		return null;
	}
}
