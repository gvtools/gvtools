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
package com.iver.andami.ui.mdiFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;
import org.gvsig.gui.beans.controls.IControl;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.Messages;
import com.iver.andami.plugins.config.generate.Label;

/**
 * <p>
 * This class contains the status bar. It contains the graphical component, and
 * the methods to manage it.
 * </p>
 * 
 * <p>
 * The status bar is divided in several areas. At the very left, there is an
 * icon and the main status text. There are three icons to show: Info, Warning
 * and Error icons. They can be set together with the main status text using the
 * methods <code>setInfoText()</code>, <code>setWarningText()</code> and
 * <code>setErrorText()</code> (and also with <code>setInfoTextTemporal()</code>
 * , etc). Then, there is a right area which contains labels and other controls.
 * Labels are set in the config.xml files and are visible or not depending on
 * the currently selected Andami window. Controls are associated to extensions,
 * and are enabled/disabled and visible/hidden depending on the associated
 * extension.
 * </p>
 * 
 */
public class NewStatusBar extends JPanel {
	private static Logger logger = Logger.getLogger(NewStatusBar.class
			.getName());
	private static final int INFO = 0;
	private static final int WARNING = 1;
	private static final int ERROR = 2;
	private JLabel lblIcon = null;
	private JLabel lblTexto = null;
	private boolean contenidoTemporal;
	private String textoAnterior;
	private int estadoAnterior;
	private ImageIcon infoIcon;
	private ImageIcon warningIcon;
	private ImageIcon errorIcon;
	private int estado;
	private JProgressBar progressBar = null;
	private HashMap<String, JLabel> idLabel = new HashMap<String, JLabel>();
	private int[] widthlabels = null;
	private HashMap<String, Object> controls = new HashMap<String, Object>();
	private JPanel controlContainer;

	/**
	 * This is the default constructor
	 */
	public NewStatusBar() {
		super();
		initialize();
		infoIcon = PluginServices.getIconTheme().get("info-icon");
		warningIcon = PluginServices.getIconTheme().get("warning-icon");
		errorIcon = PluginServices.getIconTheme().get("error-icon");

	}

	/**
	 * This method initializes the status bar. It creates the required
	 * containers and sets the layout.
	 */
	private void initialize() {
		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		JPanel container1 = new JPanel();
		this.add(container1, BorderLayout.CENTER);
		controlContainer = new JPanel();
		this.add(controlContainer, BorderLayout.EAST);

		this.setPreferredSize(new java.awt.Dimension(183, 20));
		this.setSize(new java.awt.Dimension(183, 20));
		lblIcon = new JLabel();
		lblTexto = new JLabel();
		lblTexto.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lblTexto.setHorizontalAlignment(SwingConstants.LEFT);
		lblTexto.setHorizontalTextPosition(SwingConstants.LEFT);

		FlowLayout rightLayout = new FlowLayout();
		controlContainer.setLayout(rightLayout);
		rightLayout.setHgap(1);
		rightLayout.setVgap(2);
		rightLayout.setAlignment(java.awt.FlowLayout.RIGHT);
		lblIcon.setText("");
		lblTexto.setText(Messages.getString("StatusBar.Aplicacion_iniciada"));

		FlowLayout leftLayout = new FlowLayout(FlowLayout.LEFT);
		leftLayout.setHgap(1);
		leftLayout.setVgap(2);
		container1.setLayout(leftLayout);
		container1.add(lblIcon, null);
		container1.add(getProgressBar(), null);
		container1.add(lblTexto, null);
	}

	/**
	 * Gets the status bar main text.
	 * 
	 * @return The status bar main text.
	 * @see #setInfoText(String)
	 * @see #setWarningText(String)
	 * @see #setErrorText(String)
	 * @see #setInfoTextTemporal(String)
	 * @see #setWarningTextTemporal(String)
	 * @see #setErrorTextTemporal(String)
	 */
	public String getStatusText() {
		return lblTexto.getText();
	}

	/**
	 * Restores the previous contents in the status bar main text, after the
	 * {@link #setInfoTextTemporal(String)},
	 * {@link #setWarningTextTemporal(String)} or
	 * {@link #setErrorTextTemporal(String)} have been called.
	 * 
	 * @see #setInfoTextTemporal(String)
	 * @see #setWarningTextTemporal(String)
	 * @see #setErrorTextTemporal(String)
	 */
	public void restaurarTexto() {
		contenidoTemporal = false;

		if (estadoAnterior == -1) {
			return;
		}

		switch (estadoAnterior) {
		case INFO:
			setInfoText(textoAnterior);

			break;

		case WARNING:
			setWarningText(textoAnterior);

			break;

		case ERROR:
			setErrorText(textoAnterior);

			break;
		}

		estadoAnterior = -1;
		textoAnterior = null;
	}

	/**
	 * Sets a temporary information message in the status bar, and changes the
	 * icon to an Info icon. The previous text and icon can be restored using
	 * the {@link #restaurarTexto()} method.
	 * 
	 * @param texto
	 *            The text to set
	 * @see #restaurarTexto()
	 */
	public void setInfoTextTemporal(String texto) {
		if (!contenidoTemporal) {
			textoAnterior = getStatusText();
			estadoAnterior = this.estado;
			contenidoTemporal = true;
		}

		this.estado = INFO;
		lblIcon.setIcon(infoIcon);
		lblTexto.setText(texto);
	}

	/**
	 * Sets a temporary warning message in the status bar, and changes the icon
	 * to a Warning icon. The previous text and icon can be restored using the
	 * {@link #restaurarTexto()} method.
	 * 
	 * @param texto
	 *            The text to set
	 * @see #restaurarTexto()
	 */
	public void setWarningTextTemporal(String texto) {
		if (!contenidoTemporal) {
			estadoAnterior = this.estado;
			textoAnterior = getStatusText();
			contenidoTemporal = true;
		}
		this.estado = WARNING;
		lblIcon.setIcon(warningIcon);
		lblTexto.setText(texto);
	}

	/**
	 * Sets a temporary error message in the status bar, and changes the icon to
	 * an Error icon. The previous text and icon can be restored using the
	 * {@link #restaurarTexto()} method.
	 * 
	 * @param texto
	 *            The text to set
	 * @see #restaurarTexto()
	 */
	public void setErrorTextTemporal(String texto) {
		if (!contenidoTemporal) {
			estadoAnterior = this.estado;
			textoAnterior = getStatusText();
			contenidoTemporal = true;
		}

		this.estado = ERROR;
		lblIcon.setIcon(errorIcon);
		lblTexto.setText(texto);
	}

	/**
	 * Sets a permanent info message in the status bar, and changes the
	 * permanent icon to an Info icon. If there is a temporary message showing
	 * at the moment, the message set now is not shown until the
	 * {@link #restaurarTexto()} method is called.
	 * 
	 * @param texto
	 *            The permanent info message to set
	 * @see #restaurarTexto()
	 */
	public void setInfoText(String texto) {
		if (contenidoTemporal) {
			textoAnterior = texto;
			estadoAnterior = INFO;
		} else {
			lblTexto.setText(texto);
			lblIcon.setIcon(infoIcon);
			estado = INFO;
		}
	}

	/**
	 * Sets a permanent warning message in the status bar, and changes the
	 * permanent icon to a Warning icon. If there is a temporary message showing
	 * at the moment, the message set now is not shown until the
	 * {@link #restaurarTexto()} method is called.
	 * 
	 * @param texto
	 *            The permanent warning message to set
	 * @see #restaurarTexto()
	 */
	public void setWarningText(String texto) {
		if (contenidoTemporal) {
			textoAnterior = texto;
			estadoAnterior = WARNING;
		} else {
			lblTexto.setText(texto);
			lblIcon.setIcon(warningIcon);
			estado = WARNING;
		}
	}

	/**
	 * Sets a permanent error message in the status bar, and changes the
	 * permanent icon to an Error icon. If there is a temporary message showing
	 * at the moment, the message set now is not shown until the
	 * {@link #restaurarTexto()} method is called.
	 * 
	 * @param texto
	 *            The permanent info message to set
	 * @see #restaurarTexto()
	 */
	public void setErrorText(String texto) {
		if (contenidoTemporal) {
			textoAnterior = texto;
			estadoAnterior = ERROR;
		} else {
			lblTexto.setText(texto);
			lblIcon.setIcon(errorIcon);
			estado = ERROR;
		}
	}

	/**
	 * If <code>p</code> is a value between 0 and 99, it shows a progress bar in
	 * the left area of the status bar, and sets the specified progress. If
	 * <code>p</code> is bigger than 99, it hides the progress bar.
	 * 
	 * @param p
	 *            The progress to set in the progress bar. If it is bigger than
	 *            99, the task will be considered to be finished, and the
	 *            progress bar will be hidden.
	 */
	public void setProgress(int p) {
		if (p < 100) {
			getProgressBar().setValue(p);
			getProgressBar().setVisible(true);
		} else {
			getProgressBar().setVisible(false);
		}

		getProgressBar().repaint();
	}

	/**
	 * Sets a label-set to be shown in the status bar. This method it is not
	 * intended to be used directly, because the set will be overwritten when
	 * the selected window changes. Use
	 * {@link MainFrame#setStatusBarLabels(Class, Label[])} to permanently
	 * associate a label set with a window.
	 * 
	 * @param labels
	 *            The labels to set.
	 * @see MainFrame#setStatusBarLabels(Class, Label[])
	 */
	public void setLabelSet(Label[] labels) {
		removeAllLabels();
		idLabel.clear();

		for (int i = 0; i < labels.length; i++) {
			JLabel lbl = new JLabel();
			lbl.setPreferredSize(new Dimension(labels[i].getSize(), this
					.getHeight() - 2));
			lbl.setSize(new Dimension(labels[i].getSize(), this.getHeight() - 2));
			lbl.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			lbl.setName(labels[i].getId());
			controlContainer.add(lbl);

			/*
			 * if (i != labels.length - 1){ this.add(new
			 * JSeparator(JSeparator.VERTICAL)); }
			 */
			idLabel.put(labels[i].getId(), lbl);
		}

		JLabel[] configlabels = idLabel.values().toArray(new JLabel[0]);
		widthlabels = new int[configlabels.length];

		for (int i = 0; i < labels.length; i++) {
			widthlabels[i] = configlabels[i].getWidth();
		}

		this.repaint();
	}

	/**
	 * Hides the empty labels and adjust the space in the bar.
	 */
	public void ajustar() {
		if (widthlabels == null) {
			return;
		}

		JLabel[] labels = idLabel.values().toArray(new JLabel[0]);

		/*
		 * double total = 1; for (int i = 0; i < widthlabels.length; i++) { if
		 * (labels[i].getText().compareTo("") != 0) { total += widthlabels[i]; }
		 * } double p = (ws - lblTexto.getWidth() - 20) / total;
		 */
		for (int i = 0; i < labels.length; i++) {
			// if (labels[i] instanceof JLabel){
			JLabel label = labels[i];

			if (label.getText().compareTo("") != 0) {
				label.setVisible(true);
				label.setPreferredSize(new Dimension(widthlabels[i], this
						.getHeight() - 2));
			} else {
				label.setVisible(false);

				// label.setPreferredSize(new Dimension(0,this.getHeight()));
			}

			// }
		}
	}

	/**
	 * Removes all the labels from the status bar. It does not remove the
	 * controls.
	 */
	private void removeAllLabels() {
		Component[] controlArray = controlContainer.getComponents();

		for (int i = 0; i < controlArray.length; i++) {
			if ((controlArray[i] != lblIcon) && (controlArray[i] != lblTexto)
					&& !(controlArray[i] instanceof IControl)
					&& (controlArray[i] instanceof JLabel)) {
				controlContainer.remove(controlArray[i]);
			}
		}
	}

	/**
	 * Sets the text of the provided label.
	 * 
	 * @param id
	 *            The ID of the label to modify. It is defined in the config.xml
	 *            file
	 * @param msg
	 *            The message to show in the label
	 */
	public void setMessage(String id, String msg) {
		JLabel lbl = idLabel.get(id);

		if (lbl != null) {
			lbl.setText(msg);
		} else {
			// try with controls
			try {
				IControl control = (IControl) controls.get(id);
				if (control != null)
					control.setValue(msg);
			} catch (ClassCastException ex) {
				logger.debug("no label called " + id);
			}
		}

		ajustar();
	}

	/**
	 * Sets the control identified by 'id' with the provided value.
	 * 
	 * @param id
	 *            The ID of the control to modify
	 * @param value
	 *            The value to set in the control
	 */
	public void setControlValue(String id, String value) {
		IControl control = (IControl) controls.get(id);
		if (control != null) {
			control.setValue(value);
		} else {
			logger.debug("NewStatusBar -- no control called " + id);
		}
	}

	/**
	 * This method initializes the progressBar and gets it.
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setPreferredSize(new java.awt.Dimension(100, 14));
			progressBar.setVisible(false);
			progressBar.setMinimum(0);
			progressBar.setMaximum(100);
			progressBar.setValue(50);
		}

		return progressBar;
	}

	/**
	 * Sets the width of the main message label.
	 * 
	 * @param d
	 *            The width ob the main label
	 * @deprecated
	 */
	public void setFixedLabelWidth(double d) {
		lblTexto.setPreferredSize(new Dimension((int) d, lblTexto.getHeight()));
	}

	/**
	 * Adds a control to the status bar
	 * 
	 * @param id
	 *            The ID of the control, useful to later retrive it or set its
	 *            value
	 * @param control
	 *            The control to add
	 */
	public void addControl(String id, Component control) {
		if (!controls.containsKey(control.getName())) {
			controls.put(control.getName(), control);
			controlContainer.add(control);
		} else {
			logger.debug("NewStatusBar.addControl -- control 'id' already exists"
					+ id);
		}
	}

	/**
	 * Remove a control from the status bar
	 * 
	 * @param id
	 *            The ID of the control to get
	 */
	public Component removeControl(String id) {
		try {
			Component component = (Component) controls.get(id);
			controlContainer.remove(component);
			controls.remove(id);
			return component;
		} catch (ClassCastException ex) {
			logger.debug("NewStatusBar.removeControl -- control " + id
					+ "doesn't exist");
		}
		return null;
	}

	/**
	 * Gets a control from the status bar
	 * 
	 * @param id
	 *            The ID of the control to get
	 */
	public Component getControl(String id) {
		return (Component) controls.get(id);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
