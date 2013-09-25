/*
 * Created on 14-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
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
package com.iver.cit.gvsig.project.documents.view.legend.gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.gvsig.layer.Layer;
import org.gvsig.legend.Legend;

/**
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 *         Este interfaz es el que debe cumplir cualquier panel que empleemos
 *         para confeccionar una leyenda. Se le pasa una capa para que tome su
 *         leyenda, y con el m�todo getLegend tomamos la leyenda que ha
 *         confeccionado el usuario.
 */
public interface ILegendPanel {
	/**
	 * Initializes the data required for this legend panel. That is, the layer
	 * that will adopt the changes and the current legend that this layer has.
	 * If the legend is not the type of legend that this panel manages then it
	 * initializes the panel with the default values. In case it is, then the
	 * panel should refresh its components with the current values of the
	 * Legend.
	 * 
	 * @param lyr
	 *            , target layer
	 * @param legend
	 *            , the legend currently applied to lyr
	 */
	public void setData(Layer lyr, Legend legend);

	/**
	 * Returns an instance of Legend
	 * 
	 * @return Legend, the legend result of the settings
	 */
	public Legend getLegend();

	/**
	 * Returns a brief human-readable description about what kind of legend
	 * builds this panel
	 * 
	 * @return String with a brief description
	 */
	public String getDescription();

	/**
	 * Returns the icon which should graphically describe what this panel's
	 * legend does.
	 * 
	 * @return ImageIcon with the icon to be displayed
	 */
	public ImageIcon getIcon();

	/**
	 * If this panel should appear as a subnode of the legends tree, in other
	 * words it is not a first-level node, then this method would return <b>its
	 * parent's class</b>. Otherwise, if it is a first-level node, then it will
	 * return <b>null</b>.
	 * 
	 * @return String containing the parent's title.
	 */
	public Class<? extends ILegendPanel> getParentClass();

	/**
	 * Returns the title (a human-readable one) of this panel.
	 */
	public String getTitle();

	/**
	 * <p>
	 * If this is a complete panel (it is a child node in the legend tree) to
	 * set up a legend this method will return a <b>JPanel</b> containing the
	 * necessary components. Otherwise, if it is just a classification node (it
	 * has children) in the legend tree it will return just <b>null</b>.<br>
	 * </p>
	 * 
	 * <p>
	 * If <b>null</b> is returned, the ILegendPanel that will be shown and
	 * selected each time it is selected is the very first child of this parent
	 * node.
	 * </p>
	 */
	public JPanel getPanel();

	/**
	 * Returns the class of the legend produced by this ILegendPanel.
	 */
	public Class<? extends Legend> getLegendClass();

	/**
	 * Returns <b>true</b> if this legend is applicable to this layer,
	 * <b>false</b> otherwise.
	 */
	public boolean isSuitableFor(Layer layer);
}
