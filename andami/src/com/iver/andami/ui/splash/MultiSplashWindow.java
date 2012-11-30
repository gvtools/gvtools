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

package com.iver.andami.ui.splash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.Messages;
import com.iver.andami.ui.theme.Theme;

public class MultiSplashWindow extends JWindow implements TimerCallBack,
		MouseListener {
	private static final long serialVersionUID = 572592158521767258L;
	private JProgressBar pb;
	private JLabel lblStatus;
	private Timer timer;
	private int numLogos = 0;
	private ImageIcon[] img = null;
	private Dimension splashDimension;
	private int index = 0;
	private int current;
	private int lastIndex = -1;
	private Theme theme;
	private long[] timers;

	private String version = "";
	private String[] versions = null;
	private Point point = new Point(270, 240);
	private Point[] points = null;
	private int fontsize = 18;
	private int[] fontSizes = null;
	private Color fontcolor = new Color(80, 170, 240);
	private Color[] fontColors = null;

	public MultiSplashWindow(Frame f, Theme theme, int progressBarStepCount) {
		super(f);
		this.theme = theme;
		this.timers = theme.getTimers();
		lblStatus = new JLabel(Messages.getString("SplashWindow.Iniciando"));
		lblStatus.setBorder(BorderFactory.createEtchedBorder());
		lblStatus.setBackground(Color.WHITE);

		pack();
		pb = new JProgressBar(0, progressBarStepCount);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(lblStatus, BorderLayout.NORTH);
		getContentPane().add(pb, BorderLayout.SOUTH);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		init();
		setLocation((screenSize.width / 2) - (splashDimension.width / 2),
				(screenSize.height / 2) - (splashDimension.height / 2));
		index = 0;
//		setVisible(true);
		this.addMouseListener(this);
	}

	public void init() {
		img = theme.getSplashImages();
		numLogos = img.length;

		if (numLogos == 0) {
			numLogos = 1;
			img = new ImageIcon[1];
			img[0] = PluginServices.getIconTheme().get("splash");
			timers = new long[1];
			timers[0] = 1000L;
		}
		versions = theme.getVersions();
		points = theme.getPositions();
		fontSizes = theme.getFontSizes();
		fontColors = theme.getFontColors();
		int splashWidth = img[0].getIconWidth();
		int splashHeight = img[0].getIconHeight();

		for (int i = 1; i < numLogos; i++) {
			splashWidth = Math.max(splashWidth, img[i].getIconWidth());
			splashHeight = Math.max(splashHeight, img[i].getIconHeight());
		}

		splashDimension = new Dimension(splashWidth, splashHeight);
		setSize(splashDimension.width, splashDimension.height + 45);

		start();
	}

	public void paint(Graphics g) {
		if (lastIndex == current) {
			return;
		}

		super.paint(g);

		if ((img == null) || (img[current] == null)) {
			return;
		}

		ImageIcon image = img[current];
		g.drawImage(image.getImage(), (getWidth() / 2)
				- (image.getIconWidth() / 2),
				((getHeight() / 2) - (image.getIconHeight() / 2)), this);

		Font font = new Font("SansSerif", Font.BOLD, fontsize);
		if (fontSizes.length > 0 && fontSizes[current] != 0) {
			font = new Font("SansSerif", Font.BOLD, fontSizes[current]);
		}
		g.setFont(font);

		Color color = fontcolor;
		if (fontColors.length > 0 && fontColors[current] != null) {
			color = fontColors[current];
		}
		g.setColor(color);

		String ver = version;
		if (versions.length > 0 && versions[current] != null) {
			ver = versions[current];
		}

		Point p = point;
		if (points.length > 0 && points[current] != null) {
			p = points[current];
		}
		g.drawString(PluginServices.getText(this, ver), (int) p.getX(),
				(int) p.getY());

		lastIndex = current;
	}

	public void tick() {
		current = index;
		timer.setInterval(timers[current]);
		repaint();
		index++;

		if (index >= numLogos) {
			index = 0;
		}
	}

	public void start() {
		timer = new Timer(this, 1000);
		timer.start();
	}

	/**
	 * Cierra la ventana
	 */
	public void close() {
		setVisible(false);
		stop();
		dispose();
	}

	public void stop() {
		timer.stop();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param args
	 *            DOCUMENT ME!
	 */
	public static void main(String[] args) {
		Frame frame = new Frame();
		Theme theme = new Theme();
		MultiSplashWindow ba = new MultiSplashWindow(frame, theme, 5);
		ba.setVisible(true);
		ba.setSize(500, 500);
		ba.init();
		ba.start();
	}

	/**
	 * @see com.iver.mdiApp.ui.AppLoadingListener#process(int)
	 */
	public void process(int p, String str) {
		lblStatus.setText(str);

		if (pb.getValue() != p) {
			pb.setValue(p);
		}
		doLayout();
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		this.setVisible(false);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
