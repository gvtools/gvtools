/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
 *
 * Copyright (C) 2005 IVER T.I. and Generalitat Valenciana.
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
 * $Id: SymbolSelectorListModel.java 18623 2008-02-08 11:02:05Z jdominguez $
 * $Log$
 * Revision 1.11  2007-09-19 15:36:36  jaume
 * removed unnecessary imports
 *
 * Revision 1.10  2007/09/17 15:27:21  jaume
 * *** empty log message ***
 *
 * Revision 1.9  2007/08/07 11:22:06  jvidal
 * javadoc
 *
 * Revision 1.8  2007/05/08 15:44:07  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/04/04 16:01:14  jaume
 * *** empty log message ***
 *
 * Revision 1.6  2007/04/02 00:08:05  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2007/03/28 16:44:08  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2007/03/09 11:25:00  jaume
 * Advanced symbology (start committing)
 *
 * Revision 1.4.2.4  2007/02/21 07:35:14  jaume
 * *** empty log message ***
 *
 * Revision 1.4.2.3  2007/02/09 11:00:03  jaume
 * *** empty log message ***
 *
 * Revision 1.4.2.2  2007/02/08 15:43:05  jaume
 * some bug fixes in the editor and removed unnecessary imports
 *
 * Revision 1.4.2.1  2007/01/26 13:49:03  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2007/01/16 11:52:11  jaume
 * *** empty log message ***
 *
 * Revision 1.7  2007/01/10 17:05:05  jaume
 * moved to FMap and gvSIG
 *
 * Revision 1.6  2006/11/06 16:06:52  jaume
 * *** empty log message ***
 *
 * Revision 1.5  2006/11/06 07:33:54  jaume
 * javadoc, source style
 *
 * Revision 1.4  2006/11/02 17:19:28  jaume
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/30 19:30:35  jaume
 * *** empty log message ***
 *
 * Revision 1.2  2006/10/26 16:31:21  jaume
 * GUI
 *
 * Revision 1.1  2006/10/25 10:50:41  jaume
 * movement of classes and gui stuff
 *
 * Revision 1.2  2006/10/24 22:26:18  jaume
 * *** empty log message ***
 *
 * Revision 1.1  2006/10/24 16:31:12  jaume
 * *** empty log message ***
 *
 *
 */
package com.iver.cit.gvsig.gui.styling;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.event.ListDataListener;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.geotools.styling.Style;
import org.geotools.styling.Symbolizer;

import com.iver.andami.messages.NotificationManager;
import com.iver.cit.gvsig.project.documents.view.legend.gui.SymbologyUtils;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.listManager.ListModel;
import com.iver.utiles.xml.XMLEncodingUtils;
import com.iver.utiles.xmlEntity.generate.XmlTag;

/**
 * Class SymbolSelectorListModel implements a list to select symbols.This list
 * has the property that allows the user to stablish a filter to accept or
 * reject elements for it from a directory which is also specified when teh
 * SymbolSelectorModel is created.
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class SymbolSelectorListModel implements ListModel {

	private String fileExtension;
	protected FileFilter ffilter = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.getAbsolutePath().toLowerCase()
					.endsWith(SymbolSelectorListModel.this.fileExtension);
		}
	};
	protected SelectorFilter sfilter;
	protected Vector<Style> elements;
	private ArrayList<ListDataListener> listeners;
	// protected Object currentElement;
	protected File dir;

	/**
	 * <p>
	 * Creates a new instance of the model for the list in the Symbol Selector
	 * window where the symbols are stored in the <b>dir</b> (root directory)
	 * param.<br>
	 * </p>
	 * <p>
	 * The <b>currentElement</b> defines which element is pre-selected.<br>
	 * </p>
	 * <p>
	 * The <b>filter</b> is a user defined filter used to know which elements in
	 * the folder are accepted or rejected for this list and it is used to avoid
	 * mixing marker symbols for polygons for example.<br>
	 * </p>
	 * <p>
	 * <b>fileExtension</b> param defines the extension of the file to be
	 * parsed. This is like that to enable inheritance of this class to other
	 * file selector such as StyleSelector.
	 * 
	 * @param dir
	 *            , the root dir where symbols are located.
	 * @param currentElemet
	 *            , the element to be pre-selected.
	 * @param filter
	 *            , the filter used to show or hide some elements.
	 * @param fileExtension
	 *            , file extension used for the files to be parsed.
	 */
	public SymbolSelectorListModel(File dir, SelectorFilter filter,
			String fileExtension) {
		this.fileExtension = fileExtension;
		this.dir = dir;
		this.sfilter = filter;
	}

	public Object remove(int i) throws ArrayIndexOutOfBoundsException {
		return elements.remove(i);
	}

	public void insertAt(int i, Object o) {
		if (o instanceof Style) {
			getObjects().insertElementAt((Style) o, i);
		}
	}

	public void add(Object o) {
		if (!(o instanceof Style)) {
			return;
		}

		TreeSet<Style> map = new TreeSet<Style>(new Comparator<Style>() {

			public int compare(Style sym1, Style sym2) {
				// first will always be the current symbol

				if (sym1.getDescription() == null
						&& sym2.getDescription() != null)
					return -1;
				if (sym1.getDescription() != null
						&& sym2.getDescription() == null)
					return 1;
				if (sym1.getDescription() == null
						&& sym2.getDescription() == null)
					return 1;

				int result = sym1.getDescription().getAbstract()
						.compareTo(sym2.getDescription().getAbstract());
				return (result != 0) ? result : 1; /*
													 * this will allow adding
													 * symbols with the same
													 * value for description
													 * than a previous one.
													 */
			}

		});

		map.addAll(elements);
		map.add((Style) o);
		elements = new Vector<Style>(map);

	}

	public Vector<Style> getObjects() {
		if (elements == null) {
			elements = new Vector<Style>();

			File[] ff = dir.listFiles(ffilter);
			for (int i = 0; i < ff.length; i++) {

				XMLEntity xml;
				try {

					xml = new XMLEntity(
							(XmlTag) XmlTag.unmarshal(XMLEncodingUtils
									.getReader(ff[i])));
					Symbolizer sym = SymbologyUtils.createSymbolFromXML(xml,
							ff[i].getName());
					if (sfilter.accepts(sym))
						add(sym);
				} catch (MarshalException e) {
					NotificationManager.addWarning(
							"Error in file [" + ff[i].getAbsolutePath() + "]. "
									+ "File corrupted! Skiping it...", e);
				} catch (ValidationException e) {
					NotificationManager.addWarning(
							"Error validating symbol file ["
									+ ff[i].getAbsolutePath() + "].", e);
				} catch (FileNotFoundException e) {
					// unreachable code, but anyway...
					NotificationManager.addWarning(
							"File not found: " + ff[i].getAbsolutePath(), e);
				}

			}
		}
		return elements;
	}

	public int getSize() {
		return getObjects().size();
	}

	public Object getElementAt(int index) {
		return getObjects().get(index);
	}

	public void addListDataListener(ListDataListener l) {
		if (listeners == null)
			listeners = new ArrayList<ListDataListener>();
		listeners.add(l);
	}

	public void removeListDataListener(ListDataListener l) {
		if (listeners != null)
			listeners.remove(l);
	}

}
