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

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.MessageEvent;
import com.iver.andami.messages.NotificationListener;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * Frame que escucha los eventos del sistema de mensajes de la aplicación y los
 * muestra.
 */
public class ConsolaFrame extends JPanel implements IWindow, SingletonWindow,
		NotificationListener {
	private StringBuffer info = new StringBuffer();
	private StringBuffer warn = new StringBuffer();
	private StringBuffer error = new StringBuffer();
	private StringBuffer all = new StringBuffer();
	private JEditorPane textoInfos;
	private JEditorPane textoWarnings;
	private JEditorPane textoErrores;
	private JEditorPane textoAll;

	private final static String marcaInfo = "font size='3' color='#000000'";
	private final static String marcaWarning = "font size='3' color='#800000'";
	private final static String marcaError = "font size='3' color='#FF0000'";
	private JTabbedPane tabs;
	private JPanel panelErrores;

	public static final int WIDTH = 500;
	public static final int HEIGHT = 270;

	/**
	 * Crea la consola con los mensajes producidos hasta el momento de la
	 * creación de este objeto.
	 * 
	 * @param todo
	 *            Array de todos los mensajes
	 * @param infos
	 *            Array de los mensajes de información
	 * @param warns
	 *            Array de los mensajes de warning
	 * @param errors
	 *            Array de los mensajes de error
	 */
	public ConsolaFrame() {

		this.setSize(WIDTH, HEIGHT);

		// Las cajas de texto donde van los mensajes
		textoInfos = new JEditorPane();
		textoInfos.setEditable(false);
		textoInfos.setContentType("text/html");
		textoWarnings = new JEditorPane();
		textoWarnings.setEditable(false);
		textoWarnings.setContentType("text/html");
		textoErrores = new JEditorPane();
		textoErrores.setEditable(false);
		textoErrores.setContentType("text/html");
		textoAll = new JEditorPane();
		textoAll.setEditable(false);
		textoAll.setContentType("text/html");

		JScrollPane scroll = new JScrollPane(textoAll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroll.setPreferredSize(new Dimension(500, 500));
		// scroll.setMinimumSize(new Dimension(10, 10));
		JPanel panelAll = new JPanel(new BorderLayout());
		panelAll.add(scroll, BorderLayout.CENTER);

		scroll = new JScrollPane(textoInfos);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroll.setPreferredSize(new Dimension(250, 145));
		// scroll.setMinimumSize(new Dimension(10, 10));
		JPanel panelInfos = new JPanel(new BorderLayout());
		panelInfos.add(scroll, BorderLayout.CENTER);

		scroll = new JScrollPane(textoWarnings);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroll.setPreferredSize(new Dimension(250, 145));
		// scroll.setMinimumSize(new Dimension(10, 10));
		JPanel panelWarnings = new JPanel(new BorderLayout());
		panelWarnings.add(scroll, BorderLayout.CENTER);

		scroll = new JScrollPane(textoErrores);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroll.setPreferredSize(new Dimension(250, 145));
		// scroll.setMinimumSize(new Dimension(10, 10));
		panelErrores = new JPanel(new BorderLayout());
		panelErrores.add(scroll, BorderLayout.CENTER);

		// rellenar(infos, textoInfos, info, marcaInfo);
		// rellenar(warns, textoWarnings, warn, marcaWarning);
		// rellenar(errors, textoErrores, error, marcaError);
		// rellenarTodo(todo, textoAll);

		// Las pestañas
		tabs = new JTabbedPane();
		tabs.addTab(PluginServices.getText(this, "todos"), panelAll);
		tabs.addTab(PluginServices.getText(this, "info"), panelInfos);
		tabs.addTab(PluginServices.getText(this, "warnings"), panelWarnings);
		tabs.addTab(PluginServices.getText(this, "errores"), panelErrores);
		this.setLayout(new BorderLayout());
		this.add(tabs, BorderLayout.CENTER);

	}

	/**
	 * Obtiene un string con la traza de una excepción a la consola
	 * 
	 * @param t
	 *            Elemento throwable
	 */
	private static String dumpStackTrace(Throwable t) {
		if (t == null)
			return "";
		StackTraceElement[] stes = t.getStackTrace();
		String todo = "<" + marcaWarning + ">" + t.getClass().getName() + ": "
				+ t.getLocalizedMessage() + "<" + marcaWarning + ">" + "<br/";

		for (int i = 0; i < stes.length; i++) {
			todo += ("<" + marcaWarning + ">&nbsp;&nbsp;&nbsp;&nbsp;"
					+ stes[i].toString() + "<br/");
		}

		if (t.getCause() != null) {
			todo = todo + dumpStackTrace(t.getCause());
		}

		return todo;
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#errorEvent(java.lang.String)
	 */
	public void errorEvent(MessageEvent e) {
		for (int i = 0; i < e.getMessages().length; i++) {
			String traza = dumpStackTrace(e.getExceptions()[i]);

			error.append("<" + marcaError + ">" + e.getMessages()[i] + "</"
					+ marcaError + "><br/" + traza);
			all.append("<" + marcaError + ">" + e.getMessages()[i] + "</"
					+ marcaError + "><br/" + traza);
		}
		textoErrores.setText(error.toString());
		textoAll.setText(all.toString());
		tabs.setSelectedComponent(panelErrores);
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#warningEvent(java.lang.String)
	 */
	public void warningEvent(MessageEvent e) {
		for (int i = 0; i < e.getMessages().length; i++) {
			String traza = dumpStackTrace(e.getExceptions()[i]);

			warn.append("<" + marcaWarning + ">" + e.getMessages()[i] + "</"
					+ marcaWarning + "><br/" + traza);
			all.append("<" + marcaWarning + ">" + e.getMessages()[i] + "</"
					+ marcaWarning + "><br/" + traza);
		}
		textoWarnings.setText(warn.toString());
		textoAll.setText(all.toString());
	}

	/**
	 * @see com.iver.mdiApp.NotificationListener#infoEvent(java.lang.String)
	 */
	public void infoEvent(MessageEvent e) {
		for (int i = 0; i < e.getMessages().length; i++) {
			String traza = dumpStackTrace(e.getExceptions()[i]);

			info.append("<" + marcaInfo + ">" + e.getMessages()[i] + "</"
					+ marcaInfo + "><br/" + traza);
			all.append("<" + marcaInfo + ">" + e.getMessages()[i] + "</"
					+ marcaInfo + "><br/" + traza);
		}
		textoInfos.setText(info.toString());
		textoAll.setText(all.toString());

	}

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
				| WindowInfo.RESIZABLE | WindowInfo.MAXIMIZABLE
				| WindowInfo.ICONIFIABLE);
		info.setTitle(PluginServices.getText(this, "titulo_consola"));
		return info;
	}

	public Object getWindowProfile() {
		return WindowInfo.PROPERTIES_PROFILE;
	}

}

// [eiel-gestion-excepciones]
