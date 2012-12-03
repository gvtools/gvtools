/**
 *
 */
package com.iver.core.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.plugins.config.generate.Menu;
import com.iver.andami.ui.mdiFrame.SelectableToolBar;
import com.iver.utiles.XMLEntity;

/**
 * @author cesar
 * 
 */
public class ToolbarMenus extends Extension implements ActionListener {
	private final String ACTIONCOMMANDBASE = "CHANGE_VISIBILITY-";
	private final String MENUBASE = "Ver/Toolbars/";
	private final String ENABLEDIMAGE = "images/enabled.png";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#execute(java.lang.String)
	 */
	public void execute(String actionCommand) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isVisible()
	 */
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#actionPerformed()
	 */
	public void actionPerformed(ActionEvent e) {
		String toolbarName = e.getActionCommand().substring(
				ACTIONCOMMANDBASE.length());
		javax.swing.JMenuItem menu = PluginServices.getMainFrame()
				.getMenuEntry((MENUBASE + toolbarName).split("/"));

		if (!toolbarName.equals("")) {

			boolean oldVisibility = PluginServices.getMainFrame()
					.getToolbarVisibility(toolbarName);
			if (oldVisibility == false) {
				URL icon = PluginServices.getPluginServices(this)
						.getClassLoader().getResource(ENABLEDIMAGE);
				menu.setIcon(new ImageIcon(icon));
				persistStatus(toolbarName, !oldVisibility);
			} else {
				menu.setIcon(null);
				persistStatus(toolbarName, !oldVisibility);
			}
			PluginServices.getMainFrame().setToolbarVisibility(toolbarName,
					!oldVisibility);
		}
	}

	public void initialize() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#initialize()
	 */
	public void postInitialize() {
		// restores previous session' state
		getPersistedStatus();
		// now add a menu entry for each toolbar
		SelectableToolBar[] toolBars = PluginServices.getMainFrame()
				.getToolbars();
		for (int i = toolBars.length - 1; i > 0; i--) {
			Menu menu = new Menu();
			menu.setActionCommand(ACTIONCOMMANDBASE + toolBars[i].getName());
			menu.setText(MENUBASE + toolBars[i].getName());
			if (toolBars[i].getAndamiVisibility())
				menu.setIcon(ENABLEDIMAGE);
			PluginServices.getMainFrame().addMenu(menu, this,
					PluginServices.getPluginServices(this).getClassLoader());
		}

	}

	/**
	 * Save the status of the provided toolbar.
	 * 
	 * @param toolbarName
	 *            The toolbar name whose status wants to be saved.
	 * @param visible
	 *            Whether or not the toolbar is visible.
	 */
	private void persistStatus(String toolbarName, boolean visible) {
		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();
		XMLEntity child = null;
		for (int i = xml.getChildrenCount() - 1; i >= 0; i--) {
			if (xml.getChild(i).getName()
					.equals(PluginServices.getText(this, "Toolbars")))
				child = xml.getChild(i).getChild(0);

		}
		if (child == null) {
			XMLEntity toolbars = new XMLEntity();
			toolbars.setName(PluginServices.getText(this, "Toolbars"));
			child = new XMLEntity();
			toolbars.addChild(child);
			xml.addChild(toolbars);
		}

		if (visible) {
			child.putProperty(toolbarName, "visible");
		} else {
			child.putProperty(toolbarName, "hidden");
		}
		ps.setPersistentXML(xml);

	}

	/**
	 * Reads the stored toolbars' status from plugin-persinstence.xml, and sets
	 * the toolbars accordingly.
	 * 
	 */
	private void getPersistedStatus() {
		PluginServices ps = PluginServices.getPluginServices(this);
		XMLEntity xml = ps.getPersistentXML();
		XMLEntity child = null;
		for (int i = xml.getChildrenCount() - 1; i >= 0; i--) {
			if (xml.getChild(i).getName().equals("Toolbars"))
				child = xml.getChild(i).getChild(0);

		}
		if (child != null) {
			SelectableToolBar[] toolBars = PluginServices.getMainFrame()
					.getToolbars();
			for (int i = toolBars.length - 1; i >= 0; i--) {
				if (child.contains(toolBars[i].getName()))
					toolBars[i].setAndamiVisibility(child.getStringProperty(
							toolBars[i].getName()).equals("visible"));
			}
		}
	}
}
