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
package com.iver.andami.preferences;

import java.util.Map;

import org.gvsig.gui.beans.swing.GridBagLayoutPanel;

import com.iver.utiles.extensionPoints.IExtensionBuilder;

/**
 * The abstract class that any preference page should extend.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public abstract class AbstractPreferencePage extends GridBagLayoutPanel
		implements IPreference, IExtensionBuilder {
	/**
	 * The number of components already added to the layout manager.
	 */
	protected int y;

	private String title;

	protected String parentID = null; // Por defecto, nodo raiz

	public final void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getParentID() {
		return parentID;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public Object create() {
		return this;
	}

	public Object create(Object[] args) {
		return this;
	}

	public Object create(Map<Object, Object> args) {
		return this;
	}

	/**
	 * Devuelve true si el Panel debe estar en un BorderLayout centrado o false
	 * si tiene un tamaño fijo.
	 * 
	 * @return
	 */
	public boolean isResizeable() {
		return false;
	}

	public final void saveValues() throws StoreException {
		// Store the values.
		storeValues();

		// Tell the page that its changes are already applied
		setChangesApplied();
	}

	/**
	 * <p>
	 * Gathers the configurations and stores them in the system.<br>
	 * </p>
	 * <p>
	 * <b>storeValues()</b> and <b>setChangesApplied()</b> are methods from
	 * <b>AbstractPreferencePage</b> not from <b>IPreference</b>. They both
	 * perform in combination what saveValue() should do by itself, but they
	 * exist for performance issues. In fact, you <b>should not</b> invoke them
	 * outside your PreferencePage class, you only need to code them.
	 * </p>
	 * 
	 * @throws StoreException
	 */
	public abstract void storeValues() throws StoreException;

	/**
	 * <p>
	 * After this method is invoked, the Preference page <b>must</b> return
	 * <b>true</b> as the result of invoking isValueChanged() method. It tells
	 * that the values have been saved in the system.<br>
	 * </p>
	 * <p>
	 * <b>storeValues()</b> and <b>setChangesApplied()</b> are methods from
	 * <b>AbstractPreferencePage</b> not from <b>IPreference</b>. They both
	 * perform in combination what storeValue() should by itself, but they exist
	 * for performance issues. In fact, you <b>should not</b> invoke them
	 * outside your PreferencePage class, you only need to code them.
	 * </p>
	 */
	public abstract void setChangesApplied();
}