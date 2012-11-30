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

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Interface that any entry in the application's preferences dialog must
 * implement. In addition to this interface, an abstract class is supplied to
 * ease the addition of new pages
 * 
 * @see com.iver.andami.preferences.AbstractPreferencePage
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public interface IPreference {

	/**
	 * Returns an identifier for this preferences page that is used to reference
	 * it inside the Map.
	 * 
	 * @return String, you'd typically use any kind of
	 *         <code>this.getClass().getName();</code>
	 */
	String getID();

	/**
	 * Returns an string containing the title of the preferences page. This
	 * string will be shown whether in the tree entry or in the page header.
	 * 
	 * @return String, the title of the page
	 */
	String getTitle();

	/**
	 * The page must be contained in a JPanel and whatever to be shown will be
	 * returned by this function.<br>
	 * <p>
	 * The content is added, removed and repainted automatically upon the events
	 * received from the mouse. So, you only have to care about the content and
	 * the functionality to make it <br>
	 * </p>
	 * having sense.
	 * 
	 * @return JPanel holding the contents to be shown in the page.
	 */
	JPanel getPanel();

	/**
	 * Returns the ID of the parent of this layer. If this method returns null,
	 * which means that this preferences page has no parent, this is new entry
	 * in the preferences tree, otherwise this preferences page will be hanging
	 * on the page with the ID returned by this.
	 * 
	 * @return
	 */
	String getParentID();

	/**
	 * Initializes the components of this preferences page to the last settings.
	 */
	void initializeValues();

	/**
	 * Saves the new settings
	 * 
	 * @return <b>true</b> if the values were correctly stored, <b>false</b>
	 *         otherwise.
	 * @throws StoreException
	 */
	void saveValues() throws StoreException;

	/**
	 * Restores the default values of this preferences page's settings. Values
	 * are not saved until saveValues() is executed
	 */
	void initializeDefaults();

	/**
	 * Returns the image that will be shown in the header of this preferences
	 * page
	 * 
	 * @return
	 */
	ImageIcon getIcon();

	/**
	 * Tells if this preference page has changed any value (used for storing
	 * values when necessary)
	 * 
	 * @return <b>True</b> if any value has changed, <b>false</b> otherwise.
	 */
	boolean isValueChanged();

}
