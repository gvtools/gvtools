package com.iver.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.iver.andami.Launcher;
import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.plugins.config.generate.Extensions;
import com.iver.andami.plugins.config.generate.PluginConfig;
import com.iver.andami.preferences.DlgPreferences;
import com.iver.andami.preferences.IPreference;
import com.iver.andami.preferences.IPreferenceExtension;
import com.iver.core.preferences.general.AppearancePage;
import com.iver.core.preferences.general.BrowserControlPage;
import com.iver.core.preferences.general.DirExtensionsPage;
import com.iver.core.preferences.general.ExtensionPage;
import com.iver.core.preferences.general.ExtensionsPage;
import com.iver.core.preferences.general.FolderingPage;
import com.iver.core.preferences.general.GeneralPage;
import com.iver.core.preferences.general.LanguagePage;
import com.iver.core.preferences.general.ScreenSettingsPage;
import com.iver.core.preferences.general.SkinPreferences;
import com.iver.core.preferences.network.FirewallPage;
import com.iver.core.preferences.network.NetworkPage;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

/**
 * 
 * <p>
 * Extension that provides support for visual application configuration through
 * a Preferences dialog where the user can specify its own settings for general
 * purpose aspects.
 * </p>
 * <p>
 * Adding new preference pages is made through ExtensionPoints by invoking <b>
 * this.extensionPoints.add("AplicationPreferences","YourIPreferencesClassName",
 * yourIPreferencesPage); </b> and then call
 * <b>DlgPreferences.refreshExtensionPoints();</b>
 * </p>
 * 
 * @author jaume dominguez faus - jaume.dominguez@iver.es
 * 
 */
public class PreferencesExtension extends Extension {
	private ExtensionPoints extensionPoints = ExtensionPointsSingleton
			.getInstance();

	private boolean initilizedExtensions = false;

	public void initialize() {

		registerIcons();
		initializeCoreExtensions();
	}

	private void registerIcons() {
		PluginServices.getIconTheme().registerDefault(
				"application-preferences",
				this.getClass().getClassLoader()
						.getResource("images/config.png"));

		// AppearancePage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-appearance",
				this.getClass().getClassLoader()
						.getResource("images/gnome-settings-theme.png"));

		// BrowserControlPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-browsercontrol",
				this.getClass().getClassLoader()
						.getResource("images/browser.png"));

		// DirExtensionPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-dirextension",
				this.getClass().getClassLoader()
						.getResource("images/file-manager.png"));

		// ExtensionPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-extension",
				this.getClass().getClassLoader()
						.getResource("images/emblem-work.png"));

		// ExtensionsPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-extensions",
				this.getClass().getClassLoader()
						.getResource("images/bonobo-component-browser.png"));

		// FolderingPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-foldering",
				this.getClass().getClassLoader()
						.getResource("images/folder.png"));

		// LanguagePage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-language",
				this.getClass().getClassLoader()
						.getResource("images/babel.png"));

		// ScreenSettingsPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-screensetting",
				this.getClass().getClassLoader()
						.getResource("images/resolution.png"));
		// FirewallPage.java
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-firewall",
				this.getClass().getClassLoader()
						.getResource("images/shield.png"));

		// NetworkPage.java
		//
		PluginServices.getIconTheme().registerDefault(
				"aplication-preferences-network",
				this.getClass().getClassLoader()
						.getResource("images/network.png"));
		PluginServices.getIconTheme().registerDefault(
				"application-console",
				this.getClass().getClassLoader()
						.getResource("images/console.png"));
		PluginServices.getIconTheme().registerDefault(
				"gnome-settings-theme",
				this.getClass().getClassLoader()
						.getResource("images/gnome-settings-theme.png"));
		PluginServices.getIconTheme().registerDefault(
				"browser-icon",
				this.getClass().getClassLoader()
						.getResource("images/browser.png"));
		PluginServices.getIconTheme().registerDefault(
				"file-manager",
				this.getClass().getClassLoader()
						.getResource("images/file-manager.png"));
		PluginServices.getIconTheme().registerDefault(
				"emblem-work",
				this.getClass().getClassLoader()
						.getResource("images/emblem-work.png"));
		PluginServices.getIconTheme().registerDefault(
				"folder-icon",
				this.getClass().getClassLoader()
						.getResource("images/folder.png"));
		PluginServices.getIconTheme().registerDefault(
				"kde-network-online-icon",
				this.getClass().getClassLoader()
						.getResource("images/kde-network-online.png"));
		PluginServices.getIconTheme().registerDefault(
				"kde-network-offline-icon",
				this.getClass().getClassLoader()
						.getResource("images/kde-network-offline.png"));

	}

	public void execute(String actionCommand) {
		if (!this.initilizedExtensions) {
			initializeExtensions();
			initializeExtensionsConfig();
			this.initilizedExtensions = true;
		}

		DlgPreferences dlgPreferences = PluginServices.getDlgPreferences();
		dlgPreferences.refreshExtensionPoints();
		PluginServices.getMDIManager().addWindow(dlgPreferences);
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isVisible() {
		return true;
	}

	private void initializeCoreExtensions() {
		this.extensionPoints.add("AplicationPreferences", "GeneralPage",
				new GeneralPage());
		this.extensionPoints.add("AplicationPreferences", "NetworkPage",
				new NetworkPage());
		this.extensionPoints.add("AplicationPreferences", "FirewallPage",
				new FirewallPage());
		this.extensionPoints.add("AplicationPreferences", "DirExtensionsPage",
				new DirExtensionsPage());
		this.extensionPoints.add("AplicationPreferences", "LanguagePage",
				new LanguagePage());
		this.extensionPoints.add("AplicationPreferences", "ExtensionsPage",
				new ExtensionsPage());
		this.extensionPoints.add("AplicationPreferences", "AppearancePage",
				new AppearancePage());
		this.extensionPoints.add("AplicationPreferences", "FolderingPage",
				new FolderingPage());
		this.extensionPoints.add("AplicationPreferences", "ResolutionPage",
				new ScreenSettingsPage());
		this.extensionPoints.add("AplicationPreferences", "SkinPreferences",
				new SkinPreferences());
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("linux") != -1 || os.indexOf("unix") != -1)
			this.extensionPoints.add("AplicationPreferences",
					"BrowserControlPage", new BrowserControlPage());

		// Falta los plugin
	}

	private void initializeExtensionsConfig() {
		HashMap pc = Launcher.getPluginConfig();
		ArrayList array = new ArrayList();
		Iterator iter = pc.values().iterator();

		while (iter.hasNext()) {
			array.add(((PluginConfig) iter.next()).getExtensions());
		}

		Extensions[] exts = (Extensions[]) array.toArray(new Extensions[0]);
		for (int i = 0; i < exts.length; i++) {
			for (int j = 0; j < exts[i].getExtensionCount(); j++) {
				com.iver.andami.plugins.config.generate.Extension ext = exts[i]
						.getExtension(j);
				String sExt = ext.getClassName().toString();
				// String pn = null;
				// pn = sExt.substring(0, sExt.lastIndexOf("."));
				// dlgPrefs.addPreferencePage(new PluginsPage(pn));
				// dlgPrefs.addPreferencePage(new ExtensionPage(ext));
				this.extensionPoints.add("AplicationPreferences", sExt,
						new ExtensionPage(ext));
			}
		}
	}

	/**
	 *
	 */
	private void initializeExtensions() {
		Iterator i = Launcher.getExtensionIterator();
		while (i.hasNext()) {
			Object extension = i.next();

			if (extension instanceof IPreferenceExtension) {
				IPreferenceExtension pe = (IPreferenceExtension) extension;
				IPreference[] pp = pe.getPreferencesPages();
				for (int j = 0; j < pp.length; j++) {
					this.extensionPoints.add("AplicationPreferences",
							pp[j].getID(), pp[j]);
					pp[j].initializeValues();
				}
			}
		}
	}

	public void postInitialize() {
		super.postInitialize();
		DlgPreferences dlgPreferences = PluginServices.getDlgPreferences();
		dlgPreferences.refreshExtensionPoints();
		dlgPreferences.storeValues();
		initializeExtensions();
	}
}
