/*
 * Created on 30-ene-2005
 */
package com.iver.cit.gvsig.gui.panels;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import org.cresques.Messages;
import org.cresques.ui.cts.CSSelectionPanel;
import org.geotools.referencing.CRS;
import org.gvsig.gui.beans.swing.JButton;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import com.iver.andami.PluginServices;

/**
 * @author Luis W. Sevilla (sevilla_lui@gva.es)
 */
public class ProjChooserPanel extends CRSSelectPanel {
	private CoordinateReferenceSystem currentCrs = null;
	private JLabel jLblProj = null;
	private JLabel jLblProjName = null;
	private JButton jBtnChangeProj = null;
	private boolean okPressed = false;

	/**
	 *
	 */
	public ProjChooserPanel(CoordinateReferenceSystem crs) {
		super(crs);
		setCurrentCrs(crs);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
		setPreferredSize(new java.awt.Dimension(330, 35));
		this.setSize(new java.awt.Dimension(330, 23));
		this.add(getJLblProjName(), null);
		this.add(getJLblProj(), null);
		this.add(getJBtnChangeProj(), null);
		initBtnChangeProj();
	}

	private void initBtnChangeProj() {
		getJBtnChangeProj().addActionListener(
				new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						okPressed = false;

						CSSelectionPanel csSelect = new CSSelectionPanel(
								Messages.getText("reference_system"));
						csSelect.setCrs(currentCrs);

						PluginServices.getMDIManager().addWindow(csSelect);

						if (csSelect.isOkPressed()) {
							currentCrs = csSelect.getCrs();
							jLblProj.setText(CRS.toSRS(currentCrs));
							okPressed = true;
							if (actionListener != null) {
								actionListener.actionPerformed(e);
							}
						}
					}
				});
	}

	public JLabel getJLblProjName() {
		if (jLblProjName == null) {
			jLblProjName = new JLabel("Proyeccion actual");
			jLblProjName.setText(PluginServices.getText(this,
					"__proyeccion_actual")); //$NON-NLS-1$
		}
		return jLblProjName;
	}

	public JLabel getJLabel() {
		return getJLblProjName();
	}

	public JLabel getJLblProj() {
		if (jLblProj == null) {
			jLblProj = new JLabel();
			if (currentCrs != null)
				jLblProj.setText(CRS.toSRS(currentCrs));
		}
		return jLblProj;
	}

	public void addBtnChangeProjActionListener(java.awt.event.ActionListener al) {
		jBtnChangeProj.addActionListener(al);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJBtnChangeProj() {
		if (jBtnChangeProj == null) {
			jBtnChangeProj = new JButton();
			jBtnChangeProj.setText("..."); //$NON-NLS-1$
			jBtnChangeProj.setPreferredSize(new java.awt.Dimension(26, 26));
		}
		return jBtnChangeProj;
	}

	/**
	 * @return Returns the curProj.
	 */
	public CoordinateReferenceSystem getCurrentCrs() {
		return currentCrs;
	}

	/**
	 * @param curProj
	 *            The curProj to set.
	 */
	public void setCurrentCrs(CoordinateReferenceSystem curProj) {
		this.currentCrs = curProj;
	}

	/**
	 * @return Returns the okPressed.
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
