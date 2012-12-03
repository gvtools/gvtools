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
package com.iver.core;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.MessageEvent;
import com.iver.andami.messages.NotificationListener;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * Graphic interface that appears to show an error.
 * 
 * @author Vicente Caballero Navarro
 * @author jldominguez
 */
public class NotificationDialog extends JPanel implements IWindow,
		SingletonWindow, NotificationListener {

	private static final Font ERR_MSG_FONT = new Font("Monospaced", Font.BOLD,
			11);
	private static final Font MSG_FONT = new Font("Dialog", Font.PLAIN, 12);
	private static final int ERR_MSG_MAX_LEN = 96;

	private static boolean userWantsToSeeNotifications = true;

	/*
	 * private JButton bDetails = null; private JPanel pDescription = null;
	 * private JTextArea txtDescription = null; private JButton bNoDetails =
	 * null; private JButton bAcept = null; private JScrollPane
	 * pScrollDescription = null;
	 */

	private JPanel south0Panel = null;
	private JPanel center0Panel = null;
	private JPanel center0west1Panel = null;
	private JPanel center0center1Panel = null;
	private JTextArea descriptionText = null;
	private JTextArea originalMsgText = null;

	private static ImageIcon errorIcon = null;
	private static ImageIcon messageIcon = null;
	private static String log_file = "<USER_FOLDER>" + File.separator + "gvsig"
			+ File.separator + "gvSIG.log";

	static {

		log_file = System.getProperty("user.home") + File.separator + "gvsig"
				+ File.separator + "gvSIG.log";
		errorIcon = new ImageIcon(NotificationDialog.class.getClassLoader()
				.getResource("images/gvsig_error.png"));
		messageIcon = new ImageIcon(NotificationDialog.class.getClassLoader()
				.getResource("images/gvsig_feedback.png"));
	}

	private JButton acceptButton = null;
	private JButton acceptNoShowButton = null;

	/**
	 * This is the default constructor
	 */
	public NotificationDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		Consola.consolaFrame.setSize(new java.awt.Dimension(500, 250));
		Consola.consolaFrame.setLocation(new java.awt.Point(11, 140));
		Consola.consolaFrame.setVisible(false);

		this.setLayout(new BorderLayout());
		this.setSize(ConsolaFrame.WIDTH, ConsolaFrame.HEIGHT);

		add(this.getCenter0Panel(), BorderLayout.CENTER);
		add(this.getSouth0Panel(), BorderLayout.SOUTH);
		// this.add(getBDetails(), null);
		// this.add(getPDescription(), null);
		// this.add(Consola.consolaFrame, null);
		// this.add(getDNoDetails(), null);
		// this.add(getBAcept(), null);
	}

	/**
	 * This method initializes bDetails
	 * 
	 * @return javax.swing.JButton
	 */

	/*
	 * private JButton getBDetails() { if (bDetails == null) { bDetails = new
	 * JButton(); bDetails.setBounds(new java.awt.Rectangle(315, 110, 129, 24));
	 * bDetails.setText(PluginServices.getText(this,"detalles") + "   >>>");
	 * bDetails.setVisible(true); bDetails.addActionListener(new
	 * java.awt.event.ActionListener() { public void
	 * actionPerformed(java.awt.event.ActionEvent e) {
	 * Consola.consolaFrame.setVisible(true); bDetails.setVisible(false);
	 * getDNoDetails().setVisible(true); PluginServices.getMDIManager()
	 * .getWindowInfo(NotificationDialog.this) .setHeight(325); setSize(460,
	 * 325); } }); }
	 * 
	 * return bDetails; }
	 */

	/**
	 * This method initializes pDescription
	 * 
	 * @return javax.swing.JPanel
	 */
	/*
	 * private JPanel getPDescription() { if (pDescription == null) {
	 * pDescription = new JPanel(); pDescription.setBounds(new
	 * java.awt.Rectangle(7, 5, 437, 99));
	 * pDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(
	 * null, PluginServices.getText(this,"descripcion"),
	 * javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
	 * javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
	 * pDescription.add(getPScrollDescription(), null); }
	 * 
	 * return pDescription; }
	 */

	/**
	 * This method initializes txtDescription
	 * 
	 * @return javax.swing.JTextArea
	 */
	/*
	 * private JTextArea getTxtDescription() { if (txtDescription == null) {
	 * txtDescription = new JTextArea(); //txtDescription.setPreferredSize(new
	 * java.awt.Dimension(420, 65));
	 * txtDescription.setForeground(java.awt.Color.blue);
	 * txtDescription.setBackground(java.awt.SystemColor.control);
	 * txtDescription.setEditable(false); }
	 * 
	 * return txtDescription; }
	 */

	/**
	 * @see com.iver.mdiApp.ui.MDIManager.IWindow#getModel()
	 */
	public Object getWindowModel() {
		return "consola";
	}

	/**
	 * @see com.iver.andami.ui.mdiManager.IWindow#getWindowInfo()
	 */
	public WindowInfo getWindowInfo() {
		WindowInfo info = new WindowInfo(WindowInfo.MODELESSDIALOG
				| WindowInfo.ICONIFIABLE);
		info.setWidth(ConsolaFrame.WIDTH + 10);
		info.setHeight(ConsolaFrame.HEIGHT + 30);
		info.setTitle(PluginServices.getText(this, "titulo_consola"));

		return info;
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#errorEvent(java.lang.String)
	 */
	public void errorEvent(MessageEvent e) {

		if (!userWantsToSeeNotifications) {
			return;
		}

		String aux = "[-]";
		if (e.getMessages() != null && e.getMessages().length > 0
				&& e.getMessages()[0] != null) {
			aux = e.getMessages()[0];

			if (aux.length() > ERR_MSG_MAX_LEN) {
				aux = aux.substring(0, ERR_MSG_MAX_LEN) + "... ("
						+ (aux.length() - ERR_MSG_MAX_LEN) + " more)";
			}
		}

		getTextDescription().setText(aux);
		aux = "[-]";

		if (e.getExceptions() != null && e.getExceptions().length > 0) {
			aux = getInnerMostMessage(e.getExceptions()[0]);
			if (aux.length() > ERR_MSG_MAX_LEN) {
				aux = aux.substring(0, ERR_MSG_MAX_LEN) + "... ("
						+ (aux.length() - ERR_MSG_MAX_LEN) + " more)";
			}
		}
		getOriginalErrorText().setText(aux);

		// this.doLayout();
		PluginServices.getMDIManager().restoreCursor();
		if (SwingUtilities.isEventDispatchThread()) {
			PluginServices.getMDIManager().addCentredWindow(this);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					PluginServices.getMDIManager().addCentredWindow(
							NotificationDialog.this);
				}
			});
		}

	}

	private String getInnerMostMessage(Throwable thr) {
		return getInnerMostMessage(thr, 5);
	}

	private String getInnerMostMessage(Throwable thr, int maxrec) {

		if (thr == null) {
			return "[--]";
		} else {
			if (thr.getCause() == null || thr.getCause() == thr || maxrec == 0) {

				StackTraceElement[] trace = thr.getStackTrace();
				String tracestr = "-";
				if (trace != null && trace.length > 0
						&& trace[0].getFileName() != null) {
					tracestr = trace[0].getFileName();
				}

				if (thr.getMessage() == null) {
					return thr.getClass().getSimpleName() + " (" + tracestr
							+ ")";
				} else {
					return (thr.getClass().getSimpleName() + ": " + thr
							.getMessage()) + " (" + tracestr + ")";
				}
			} else {
				return getInnerMostMessage(thr.getCause(), maxrec - 1);
			}

		}
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#warningEvent(java.lang.String)
	 */
	public void warningEvent(MessageEvent e) {
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#infoEvent(java.lang.String)
	 */
	public void infoEvent(MessageEvent e) {
	}

	/**
	 * This method initializes dNoDetails
	 * 
	 * @return javax.swing.JButton
	 */
	/*
	 * private JButton getDNoDetails() { if (bNoDetails == null) { bNoDetails =
	 * new JButton(); bNoDetails.setVisible(false); bNoDetails.setBounds(new
	 * java.awt.Rectangle(315, 110, 128, 24)); bNoDetails.setText("<<<   " +
	 * PluginServices.getText(this,"detalles"));
	 * bNoDetails.addActionListener(new java.awt.event.ActionListener() { public
	 * void actionPerformed(java.awt.event.ActionEvent e) {
	 * bDetails.setVisible(true); bNoDetails.setVisible(false);
	 * Consola.consolaFrame.setVisible(false); PluginServices.getMDIManager()
	 * .getWindowInfo(NotificationDialog.this) .setHeight(175); setSize(460,
	 * 175); } }); }
	 * 
	 * return bNoDetails; }
	 */

	/**
	 * This method initializes bAcept
	 * 
	 * @return javax.swing.JButton
	 */

	/*
	 * private JButton getBAcept() { if (bAcept == null) { bAcept = new
	 * JButton(); bAcept.setBounds(new java.awt.Rectangle(10, 110, 296, 24));
	 * bAcept.setText(PluginServices.getText(this,"aceptar"));
	 * bAcept.addActionListener(new java.awt.event.ActionListener() { public
	 * void actionPerformed(java.awt.event.ActionEvent e) {
	 * PluginServices.getMDIManager().closeWindow(NotificationDialog.this); }
	 * }); }
	 * 
	 * return bAcept; }
	 */

	/**
	 * This method initializes pScrollDescription
	 * 
	 * @return javax.swing.JScrollPane
	 */

	/*
	 * private JScrollPane getPScrollDescription() { if (pScrollDescription ==
	 * null) { pScrollDescription = new JScrollPane();
	 * pScrollDescription.setPreferredSize(new java.awt.Dimension(420,67));
	 * pScrollDescription.setAutoscrolls(true);
	 * pScrollDescription.setViewportView(getTxtDescription()); }
	 * 
	 * return pScrollDescription; }
	 */

	public Object getWindowProfile() {
		return WindowInfo.PROPERTIES_PROFILE;
	}

	public JPanel getSouth0Panel() {
		if (south0Panel == null) {
			south0Panel = new JPanel();
			south0Panel.setLayout(new FlowLayout());
			south0Panel.add(this.getAcceptButton());
			south0Panel.add(this.getAcceptNoShowButton());
		}
		return south0Panel;
	}

	public JPanel getCenter0Panel() {
		if (center0Panel == null) {
			center0Panel = new JPanel();
			center0Panel.setLayout(new BorderLayout());
			center0Panel.add(getCenter0center1Panel(), BorderLayout.CENTER);
			center0Panel.add(getCenter0west1Panel(), BorderLayout.WEST);
		}
		return center0Panel;
	}

	public JPanel getCenter0west1Panel() {
		if (center0west1Panel == null) {
			center0west1Panel = new JPanel();
			center0west1Panel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			// ===========================
			c.gridy = 0;
			c.anchor = GridBagConstraints.PAGE_END;
			center0west1Panel.add(new JLabel(errorIcon), c);
			c.gridy = 1;
			center0west1Panel.add(new JLabel(
					"                                 "), c);
			c.gridy = 2;
			c.anchor = GridBagConstraints.PAGE_START;
			center0west1Panel.add(new JLabel(messageIcon), c);
		}
		return center0west1Panel;
	}

	public JPanel getCenter0center1Panel() {
		if (center0center1Panel == null) {

			center0center1Panel = new JPanel();
			center0center1Panel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			// ===========================
			c.gridy = 0;
			JTextArea tarea1 = new JTextArea();
			tarea1.setFont(MSG_FONT);
			tarea1.setLineWrap(true);
			tarea1.setWrapStyleWord(true);
			tarea1.setText(PluginServices
					.getText(this,
							"gvsig_had_problem_process_failed_send_log_error_message_is"));
			tarea1.setEditable(false);
			tarea1.setBackground(this.getBackground());
			center0center1Panel.add(tarea1, c);

			c.weightx = 0.1;
			c.gridx = 1;
			center0center1Panel.add(new JLabel(" "), c);

			c.weightx = 1;
			c.gridx = 0;
			c.gridy = 1;
			center0center1Panel.add(new JLabel(" "), c);
			c.gridy = 2;
			center0center1Panel.add(getTextDescription(), c);
			c.gridy = 3;
			center0center1Panel.add(new JLabel(" "), c);
			c.gridy = 4;
			center0center1Panel.add(getOriginalErrorText(), c);
			c.gridy = 5;
			center0center1Panel.add(new JLabel(" "), c);

			c.gridy = 6;
			JTextArea tarea2 = new JTextArea();
			tarea2.setFont(MSG_FONT);
			tarea2.setWrapStyleWord(true);
			tarea2.setEditable(false);
			tarea2.setLineWrap(true);
			tarea2.setBackground(this.getBackground());
			tarea2.setText(PluginServices.getText(this,
					"If_msg_doesnt_help_recommend_send_log_file_in")
					+ ":\n\n"
					+ log_file);
			center0center1Panel.add(tarea2, c);
		}
		return center0center1Panel;
	}

	private JTextArea getTextDescription() {

		if (descriptionText == null) {
			descriptionText = new JTextArea();
			// txtDescription.setPreferredSize(new Dimension(320, 80));
			descriptionText.setFont(ERR_MSG_FONT);
			descriptionText.setLineWrap(true);
			descriptionText.setWrapStyleWord(false);
			descriptionText.setEditable(false);
			descriptionText.setBackground(this.getBackground());
		}
		return descriptionText;
	}

	private JTextArea getOriginalErrorText() {

		if (originalMsgText == null) {
			originalMsgText = new JTextArea();
			// txtDescription.setPreferredSize(new Dimension(320, 80));
			originalMsgText.setFont(ERR_MSG_FONT);
			originalMsgText.setLineWrap(true);
			originalMsgText.setWrapStyleWord(false);
			originalMsgText.setEditable(false);
			originalMsgText.setBackground(this.getBackground());
		}
		return originalMsgText;
	}

	public JButton getAcceptButton() {
		if (acceptButton == null) {
			acceptButton = new JButton();
			acceptButton.setText(PluginServices.getText(this, "aceptar"));
			acceptButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PluginServices.getMDIManager().closeWindow(
							NotificationDialog.this);
				}
			});

		}
		return acceptButton;
	}

	public JButton getAcceptNoShowButton() {
		if (acceptNoShowButton == null) {
			acceptNoShowButton = new JButton();
			acceptNoShowButton.setText(PluginServices.getText(this,
					"Dont_show_message_again"));
			acceptNoShowButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							userWantsToSeeNotifications = false;
							PluginServices.getMDIManager().closeWindow(
									NotificationDialog.this);
						}
					});

		}
		return acceptNoShowButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

// [eiel-gestion-excepciones]