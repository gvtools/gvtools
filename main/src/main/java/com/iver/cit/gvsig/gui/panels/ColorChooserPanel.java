/*
 * The Unified Mapping Platform (JUMP) is an extensible, interactive GUI
 * for visualizing and manipulating spatial features with geometry and attributes.
 *
 * Copyright (C) 2003 Vivid Solutions
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * For more information, contact:
 *
 * Vivid Solutions
 * Suite #1A
 * 2328 Government Street
 * Victoria BC  V8T 5G5
 * Canada
 *
 * (250)385-6040
 * www.vividsolutions.com
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
package com.iver.cit.gvsig.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.iver.andami.PluginServices;

/**
 * A custom Colour Scheme Browser - custom choices based on JColorChooser.
 */

public class ColorChooserPanel extends JPanel {
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private JButton changeButton = new JButton();

	// {DEFECT-PREVENTION} In accessors, it's better to use
	// "workbench = newWorkbench"
	// rather than "this.workbench = workbench", because otherwise if you
	// misspell the
	// argument, Java won't complain! We should do this everywhere. [Jon Aquino]
	private JPanel outerColorPanel = new JPanel();
	private ColorPanel colorPanel = new ColorPanel();
	private Color color = Color.black;
	// private int alpha;
	private Dimension dim = new Dimension(100, 22);
	private Dimension dim2 = new Dimension(dim.width, dim.height * 2 + 5);
	private ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
	private boolean withTransp;
	private boolean withTranspPerc;
	private boolean withNoFill;
	private JCheckBox chkUseColor;
	private JSlider sldTransparency;
	private JLabel lblTransparency;
	private boolean muteSldTransparency = false;
	private double perc = 0.392156863;/* 100/255 */
	private ChangeListener sldAction = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			if (!muteSldTransparency) {
				int alphaValue = sldTransparency.getValue();
				setAlpha(alphaValue);

				if (withTranspPerc) {
					int percValue = (int) (alphaValue * perc);
					lblTransparency.setText(String.valueOf(percValue) + "%");
				}
				fireActionPerformed();
			}
		}

	};

	public ColorChooserPanel() {
		this(false);
	}

	/**
	 * Constructor method
	 * 
	 * @param withTransparencySlider
	 *            boolean that specifies if the user wants a slider with the
	 *            color chooser panel to control the transparency of the
	 *            selected color.Also, it will appear a text which specifies the
	 *            transparency percentage.
	 * @param withNoFill
	 *            boolean that specifies if the color chooser panel allows a
	 *            null value for the color selected (true case). If this values
	 *            is false, when the color selected is null then black color is
	 *            assigned automatically.
	 */
	public ColorChooserPanel(boolean withTransparencySlider) {
		this(withTransparencySlider, false);

	}

	/**
	 * Constructor method
	 * 
	 * @param withTransparencySlider
	 *            boolean that specifies if the user wants a slider with the
	 *            color chooser panel to control the transparency of the
	 *            selected color.
	 * @param withNoFill
	 *            boolean that specifies if the color chooser panel allows a
	 *            null value for the color selected (true case). If this values
	 *            is false, when the color selected is null then black color is
	 *            assigned automatically.
	 */
	public ColorChooserPanel(boolean withTransparencySlider, boolean withNoFill) {

		this.withTransp = withTransparencySlider;
		this.withTranspPerc = withTransparencySlider;
		this.withNoFill = withNoFill;

		try {
			if (withTransp) {
				sldTransparency = new JSlider(0, 255);
				sldTransparency.addChangeListener(sldAction);
				int width = withNoFill ? dim2.width - 40 : dim2.width;
				sldTransparency.setPreferredSize(new Dimension(width - 5,
						dim2.height / 2));
				sldTransparency.setToolTipText(PluginServices.getText(this,
						"transparencia"));

				if (withTranspPerc) {
					lblTransparency = new JLabel();
					int percValue = (int) (sldTransparency.getValue() * perc);
					lblTransparency.setText(String.valueOf(percValue) + "%");
					lblTransparency.setPreferredSize(new Dimension(40, 20));
				}

				sldTransparency.setValue(255);

			}
			jbInit();
			colorPanel.setLineColor(null);
			changeButton.setToolTipText(PluginServices.getText(this, "browse"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		changeButton.setText("...");
		changeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeButton_actionPerformed(e);
			}
		});
		outerColorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		outerColorPanel.setPreferredSize(new Dimension(dim.width / 2,
				dim.height));
		outerColorPanel.setBackground(Color.white);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		pane.add(outerColorPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		pane.add(changeButton, c);
		outerColorPanel.add(colorPanel);

		if (withNoFill) {
			chkUseColor = new JCheckBox();
			chkUseColor.setSelected(true);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;

			chkUseColor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					useColor_actionPerformed(e);
				}
			});
			pane.add(chkUseColor, c);
		}

		if (withTransp) {

			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 3;
			c.gridx = 1;
			c.gridy = 1;
			pane.add(sldTransparency, c);

			if (withTranspPerc) {
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridwidth = 3;
				c.gridx = 4;
				c.gridy = 1;
				pane.add(lblTransparency, c);
			}

		}

		add(pane);
	}

	private void changeButton_actionPerformed(ActionEvent e) {
		Color newColor = JColorChooser.showDialog(
				SwingUtilities.windowForComponent(this),
				PluginServices.getText(this, "choose_color"), color);

		if (newColor == null) {
			return;
		}

		setColor(newColor);
		fireActionPerformed();
	}

	/**
	 * Returns true if the check box of the color chooser panel is selected.
	 * False otherwise.
	 * 
	 * @return boolean true if the check box is selected. Otherwise, false.
	 */
	public boolean getUseColorisSelected() {
		if (withNoFill)
			return chkUseColor.isSelected();
		return false;
	}

	/**
	 * Sets the check box of the color chooser panel
	 * 
	 * @param b
	 *            boolean true if the user wants to select it.Otherwise, false.
	 */
	public void setUseColorIsSelected(boolean b) {
		if (withNoFill)
			chkUseColor.setSelected(b);
	}

	/**
	 * Controls the events when the check box of the color chooser panel is
	 * modified
	 * 
	 * @param e
	 *            Action Event
	 */
	private void useColor_actionPerformed(ActionEvent e) {
		if (chkUseColor.isSelected())
			setColor(color);
		else
			setColor(null);
		fireActionPerformed();
	}

	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	public void removeActionListener(ActionListener l) {
		actionListeners.remove(l);
	}

	protected void fireActionPerformed() {
		for (Iterator<ActionListener> i = actionListeners.iterator(); i
				.hasNext();) {
			i.next().actionPerformed(new ActionEvent(this, 0, null));
		}
	}

	/**
	 * Sets the color of the chooser panel.
	 * 
	 * @param color
	 *            Color to be selected.
	 */
	public void setColor(Color color) {

		if (color != null) {
			this.color = color;
			updateColorPanel();
		}

	}

	/**
	 * Sets the alpha value of the color selected in the color chooser panel.
	 * 
	 * @param alpha
	 *            Alpha value of the color.
	 */
	public void setAlpha(int alpha) {
		muteSldTransparency = true;

		if (color != null) {
			int a = withTransp ? 255 : alpha;
			color = new Color(color.getRed(), color.getGreen(),
					color.getBlue(), a);
		}

		if (withTransp) {
			sldTransparency.setValue(alpha);
			sldTransparency.validate();
			int percValue = (int) (alpha * perc);
			lblTransparency.setText(String.valueOf(percValue) + "%");
		}

		updateColorPanel();
		muteSldTransparency = false;
		// fireActionPerformed();
	}

	public int getAlpha() {
		if (withTransp) {
			return sldTransparency.getValue();
		} else {
			return color.getAlpha();
		}

	}

	private void updateColorPanel() {
		colorPanel.setFillColor(color);
		colorPanel.repaint();
	}

	/**
	 * Obtains the selected color in the color chooser panel.
	 * 
	 * @return color the selected color
	 */
	public Color getColor() {
		return color;
	}

	public void disabledWithTransparency() {
		changeButton.setEnabled(false);
		if (withNoFill)
			chkUseColor.setEnabled(false);

	}

	@Override
	public void setEnabled(boolean newEnabled) {

		changeButton.setEnabled(newEnabled);
		if (withTransp) {
			sldTransparency.setEnabled(newEnabled);
		}
		if (withTranspPerc) {
			lblTransparency.setEnabled(newEnabled);
		}
		if (withNoFill) {
			chkUseColor.setEnabled(newEnabled);
		}

	}
}