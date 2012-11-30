/* gvSIG. Sistema de Información Geográfica de la Generalitat Valenciana
 *
 * Copyright (C) 2004-2007 IVER T.I. and Generalitat Valenciana.
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
package com.iver.andami;

import java.awt.Component;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.iver.andami.authentication.IAuthentication;
import com.iver.andami.iconthemes.IIconTheme;
import com.iver.andami.iconthemes.IconThemeManager;
import com.iver.andami.messages.Messages;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.ExclusiveUIExtension;
import com.iver.andami.plugins.ExtensionDecorator;
import com.iver.andami.plugins.IExtension;
import com.iver.andami.plugins.PluginClassLoader;
import com.iver.andami.preferences.DlgPreferences;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiFrame.MainFrame;
import com.iver.andami.ui.mdiManager.MDIManager;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.swing.threads.IMonitorableTask;
import com.iver.utiles.swing.threads.IProgressMonitorIF;
import com.iver.utiles.swing.threads.TaskMonitorTimerListener;
import com.iver.utiles.swing.threads.UndefinedProgressMonitor;

/**
 * Provides services to Plugins. Each plugin has an associated PluginServices
 * object, which provides specific services. Main of them: translations,
 * logging, access to plugin's resources, background tasks, clipboard access and
 * data persistence.
 * 
 * @author Fernando González Cortés
 */
public class PluginServices {
	private static Logger logger = Logger.getLogger(PluginServices.class
			.getName());

	private static String[] arguments;

	private static IAuthentication authentication;

	private static ExclusiveUIExtension exclusiveUIExtension = null;

	private PluginClassLoader loader;

	private XMLEntity persistentXML;

	/**
	 * Creates a new PluginServices objetct.
	 * 
	 * @param loader
	 *            The Plugin's ClassLoader.
	 */
	public PluginServices(PluginClassLoader loader) {
		this.loader = loader;
	}

	/**
	 * Returns the message in the current's locale language corresponding to the
	 * provided translation key. The key-message pairs are obtained from the
	 * plugin's translation files (text_xx.properties files).
	 * 
	 * @param key
	 *            Translation key whose associated message is to be obtained
	 * 
	 * @return The message associated with the provided key, in one of the
	 *         current locale languages, or the key if the translation is not
	 *         found.
	 */
	public String getText(String key) {
		if (key == null)
			return null;
		String translation = org.gvsig.i18n.Messages.getText(key, false);
		if (translation != null)
			return translation;
		else {
			logger.warn("Plugin "
					+ getPluginName()
					+ " -- "
					+ org.gvsig.i18n.Messages.getText(
							"No_se_encontro_la_traduccion_para", false) + " "
					+ key);
			return key;
		}
	}

	/**
	 * Gets the plugin's classloader.
	 * 
	 * @return Returns the loader.
	 */
	public PluginClassLoader getClassLoader() {
		return loader;
	}

	/**
	 * Gets the plugin's name
	 * 
	 * @return The plugin's name
	 */
	String getPluginName() {
		return loader.getPluginName();
	}

	/**
	 * Gets a reference to the PluginServices object associated with the plugin
	 * containing the provided class.
	 * 
	 * Obtienen una referencia al PluginServices del plugin cuyo nombre se pasa
	 * como parámetro
	 * 
	 * @param pluginClassInstance
	 *            An instance of a class. This class is contained in a plugin,
	 *            whose services are to be obtained
	 * 
	 * @return The PluginServices object associated to the containing plugin
	 * 
	 * @throws RuntimeException
	 *             If the parameter was not loaded from a plugin
	 */
	public static PluginServices getPluginServices(Object pluginClassInstance) {
		try {
			PluginClassLoader loader = (PluginClassLoader) pluginClassInstance
					.getClass().getClassLoader();

			return Launcher.getPluginServices(loader.getPluginName());
		} catch (ClassCastException e) {
			/*
			 * throw new RuntimeException( "Parameter is not a plugin class
			 * instance");
			 */
			return null;
		}
	}

	/**
	 * Gets a reference to the PluginServices object associated with the
	 * provided plugin.
	 * 
	 * @param pluginName
	 *            Plugin's name whose services are going to be used
	 * 
	 * @return The PluginServices object associated with the provided plugin.
	 */
	public static PluginServices getPluginServices(String pluginName) {
		return Launcher.getPluginServices(pluginName);
	}

	/**
	 * Gets the window manager (MDIManager).
	 * 
	 * @return A reference to the window manager (MDIManager).
	 */
	public static MDIManager getMDIManager() {
		return Launcher.getFrame().getMDIManager();
	}

	/**
	 * Gets the application's main frame.
	 * 
	 * @return A reference to the application's main window
	 */
	public static MainFrame getMainFrame() {
		return Launcher.getFrame();
	}

	public static void registerKeyStroke(KeyStroke key, KeyEventDispatcher a) {
		GlobalKeyEventDispatcher.getInstance().registerKeyStroke(key, a);
	}

	public static void unRegisterKeyStroke(KeyStroke key) {
		GlobalKeyEventDispatcher.getInstance().removeKeyStrokeBinding(key);
	}

	/**
	 * Gets the instance of the extension whose class is provided.
	 * 
	 * @param extensionClass
	 *            Class of the extension whose instance is to be returned
	 * 
	 * @return The instance of the extension, or null if the instance does not
	 *         exist.Instancia de la extensión o null en caso de que no haya una
	 */
	public static IExtension getExtension(Class<?> extensionClass) {
		ExtensionDecorator extAux = Launcher.getClassesExtensions().get(
				extensionClass);
		try {
			return extAux.getExtension();
		} catch (NullPointerException ex) {
			return null;
		}
	}

	/**
	 * Gets a reference to the Extension Decorator which adds some extra options
	 * to the basic extension interface.
	 * 
	 * @param extensionClass
	 *            The class of the extension whose decorator is to be returned
	 * @return The ExtensionDecorator associated with the provided extension, or
	 *         null if the extension does not exist.
	 */
	public static ExtensionDecorator getDecoratedExtension(
			Class<?> extensionClass) {
		return Launcher.getClassesExtensions().get(extensionClass);
	}

	/**
	 * Returns an array containing references to all the loaded extensions.
	 * 
	 * @return ExtensionDecorator[] An array of ExtensionDecorators (each
	 *         Decorator contains one extension).
	 */
	public static ExtensionDecorator[] getDecoratedExtensions() {
		HashMap<Class<?>, ExtensionDecorator> map = Launcher
				.getClassesExtensions();
		ExtensionDecorator[] extensions = map.values().toArray(
				new ExtensionDecorator[0]);
		return extensions;
	}

	/**
	 * Gets an iterator with all the loaded Extensions.
	 * 
	 * @return Iterator over the decorated extensions (each member of the
	 *         iterator is an ExtensionDecorator, which in turn contains one
	 *         IExtension object).
	 */
	public static Iterator<ExtensionDecorator> getExtensions() {
		return Launcher.getClassesExtensions().values().iterator();
	}

	/**
	 * Returns the message in the current's locale language corresponding to the
	 * provided translation key. The key-message pairs are obtained from the
	 * plugin's translation files (text_xx.properties files).
	 * 
	 * @param pluginObject
	 *            Any object which was loaded from a plugin
	 * 
	 * @param key
	 *            Translation key whose associated message is to be obtained
	 * 
	 * @return The message associated with the provided key, in one of the
	 *         current locale languages, or the key if the translation is not
	 *         found.
	 */
	public static String getText(Object pluginObject, String key) {
		if (key == null)
			return null;
		String translation = org.gvsig.i18n.Messages.getText(key, false);
		if (translation != null)
			return translation;
		else {
			logger.warn(org.gvsig.i18n.Messages.getText(
					"No_se_encontro_la_traduccion_para", false) + " " + key);
			return key;
		}
	}

	/**
	 * Sets the XML data which should be saved on disk for this plugin. This
	 * data can be retrieved on following sessions.
	 * 
	 * @param The
	 *            XMLEntity object containing the data to be persisted.
	 * 
	 * @see PluginServices.getPersistentXML()
	 * @see XMLEntity
	 */
	public void setPersistentXML(XMLEntity entity) {
		persistentXML = entity;
	}

	/**
	 * Gets the XML data which was saved on previous sessions for this plugins.
	 * 
	 * @return An XMLEntity object containing the persisted data
	 */
	public XMLEntity getPersistentXML() {
		if (persistentXML == null) {
			persistentXML = new XMLEntity();
		}
		return persistentXML;
	}

	/**
	 * Añade un listener a un popup menu registrado en el config.xml de algún
	 * plugin
	 * 
	 * @param name
	 *            Nombre del menú contextual
	 * @param c
	 *            Componente que desplegará el menú cuando se haga click con el
	 *            botón derecho
	 * @param listener
	 *            Listener que se ejecutará cuando se seleccione cualquier
	 *            entrada del menú
	 * 
	 * @throws RuntimeException
	 *             Si la interfaz no está preparada todavía. Sólo puede darse
	 *             durante el arranque
	 */
	public void addPopupMenuListener(String name, Component c,
			ActionListener listener) {
		MDIFrame frame = Launcher.getFrame();

		if (frame == null) {
			throw new RuntimeException("MDIFrame not loaded yet");
		}

		frame.addPopupMenuListener(name, c, listener, loader);
	}

	/**
	 * Gets the plugin's root directory.
	 * 
	 * @return A File pointing to the plugin's root directory.
	 */
	public File getPluginDirectory() {
		return new File(Launcher.getPluginsDir() + File.separator
				+ getPluginName());
	}

	/**
	 * Runs a background task. The events produced on the main frame will be
	 * inhibited.
	 * 
	 * @param r
	 *            The task to run.
	 * 
	 * @return The Thread on which the task is executed.
	 */
	public static Thread backgroundExecution(Runnable r) {
		Thread t = new Thread(new RunnableDecorator(r));
		t.start();

		return t;
	}

	/**
	 * Runs a backbround task. This task may be monitored and canceled, and does
	 * not inhibit any event.
	 * 
	 * @param task
	 *            The task to run.
	 */
	public static void cancelableBackgroundExecution(final IMonitorableTask task) {
		final com.iver.utiles.swing.threads.SwingWorker worker = new com.iver.utiles.swing.threads.SwingWorker() {
			public Object construct() {
				try {
					task.run();
					return task;
				} catch (Exception e) {
					NotificationManager.addError(null, e);
				}
				return null;
			}

			/**
			 * Called on the event dispatching thread (not on the worker thread)
			 * after the <code>construct</code> method has returned.
			 */
			public void finished() {
				task.finished();
			}
		};

		Component mainFrame = (Component) PluginServices.getMainFrame();

		IProgressMonitorIF progressMonitor = null;
		String title = getText(null, "PluginServices.Procesando");
		progressMonitor = new UndefinedProgressMonitor((Frame) mainFrame, title);
		progressMonitor.setIndeterminated(!task.isDefined());
		progressMonitor.setInitialStep(task.getInitialStep());
		progressMonitor.setLastStep(task.getFinishStep());
		progressMonitor.setCurrentStep(task.getCurrentStep());
		progressMonitor.setMainTitleLabel(task.getStatusMessage());
		progressMonitor.setNote(task.getNote());
		progressMonitor.open();
		int delay = 500;
		TaskMonitorTimerListener timerListener = new TaskMonitorTimerListener(
				progressMonitor, task);
		Timer timer = new Timer(delay, timerListener);
		timerListener.setTimer(timer);
		timer.start();

		worker.start();

	}

	/**
	 * Closes the application. Cleanly exits from the application: terminates
	 * all the extensions, then exits.
	 * 
	 */
	public static void closeApplication() {
		Launcher.closeApplication();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @author Fernando González Cortés
	 */
	private static class RunnableDecorator implements Runnable {
		private Runnable r;

		/**
		 * Crea un nuevo RunnableDecorator.
		 * 
		 * @param r
		 *            DOCUMENT ME!
		 */
		public RunnableDecorator(Runnable r) {
			this.r = r;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						try {
							r.run();
						} catch (RuntimeException e) {
							NotificationManager.addError(
									Messages.getString("PluginServices.Bug_en_el_codigo"),
									e);
						} catch (Error e) {
							NotificationManager.addError(
									Messages.getString("PluginServices.Error_grave_de_la_aplicación"),
									e);
						}
					}
				});
			} catch (InterruptedException e) {
				NotificationManager
						.addWarning(
								Messages.getString("PluginServices.No_se_pudo_poner_el_reloj_de_arena"),
								e);
			} catch (InvocationTargetException e) {
				NotificationManager
						.addWarning(
								Messages.getString("PluginServices.No_se_pudo_poner_el_reloj_de_arena"),
								e);
			}
		}
	}

	/**
	 * Gets an array containing the application's startup arguments. <br>
	 * <br>
	 * Usually: <code>appName plugins-directory [locale] [other args]</code>
	 * 
	 * @return the original arguments that Andami received. (app-name
	 *         plugins-directory, locale, etc)
	 */
	public static String[] getArguments() {
		return arguments;
	}

	/**
	 * Replaces the original Andami arguments with the provided arguments.
	 * 
	 * @param arguments
	 *            An array of String, each element of the array represents one
	 *            argument.
	 */
	public static void setArguments(String[] arguments) {
		PluginServices.arguments = arguments;
	}

	/**
	 * Returns the value of a command line named argument. <br>
	 * <br>
	 * The argument format is: <br>
	 * -{argumentName}={argumentValue} <br>
	 * <br>
	 * example: <br>
	 * ' -language=en '
	 * 
	 * @return String The value of the argument
	 */
	public static String getArgumentByName(String name) {
		for (int i = 2; i < PluginServices.arguments.length; i++) {
			int index = PluginServices.arguments[i].indexOf(name + "=");
			if (index != -1)
				return PluginServices.arguments[i].substring(index
						+ name.length() + 1);
		}
		return null;
	}

	/**
	 * Gets the logger. The logger is used to register important events (errors,
	 * etc), which are stored on a file which can be checked later.
	 * 
	 * @return A Logger object.
	 * @see Logger object from the Log4j library.
	 * 
	 */
	public static Logger getLogger() {
		return logger;
	}

	public static IAuthentication getAuthentication() {
		return authentication;
	}

	public static void setAuthentication(IAuthentication authen) {
		authentication = authen;
	}

	public static DlgPreferences getDlgPreferences() {
		return DlgPreferences.getInstance();
	}

	/**
	 * Stores the provided text data on the clipboard.
	 * 
	 * @param data
	 *            An String containing the data to be stored on the clipboard.
	 */
	public static void putInClipboard(String data) {
		StringSelection ss = new StringSelection(data);

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
	}

	/**
	 * Gets text data from the Clipboard, if available.
	 * 
	 * @return An String containing the clipboard's data, or <code>null</code>
	 *         if the data was not available.
	 */
	public static String getFromClipboard() {

		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard()
					.getContents(null).getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/**
	 * Gets the ExclusiveUIExtension, an special extension which will take
	 * control over the UI and will decide which extensions may be
	 * enable/disabled or visible/hidden.
	 * 
	 * @return If an ExclusiveUIExtension was set, return this extension.
	 *         Otherwise, return <code>null</code>.
	 * 
	 * @see com.iver.andami.Launcher#initializeExclusiveUIExtension()
	 * @see com.iver.andami.plugins.IExtension#isEnabled(IExtension extension)
	 * @see com.iver.andami.plugins.IExtension#isVisible(IExtension extension)
	 */
	public static ExclusiveUIExtension getExclusiveUIExtension() {
		return PluginServices.exclusiveUIExtension;
	}

	/**
	 * Sets the ExclusiveUIExtension, an special extension which will take
	 * control over the UI and will decide which extensions may be
	 * enable/disabled or visible/hidden. <br>
	 * <br>
	 * The ExclusiveUIExtension is normally set by the following Andami startup
	 * argument: <br>
	 * <br>
	 * <code>ExclusiveUIExtension=ExtensionName</code>
	 * 
	 * @see com.iver.andami.Launcher#initializeExclusiveUIExtension()
	 * @see com.iver.andami.plugins.IExtension#isEnabled(IExtension extension)
	 * @see com.iver.andami.plugins.IExtension#isVisible(IExtension extension)
	 */
	public static void setExclusiveUIExtension(ExclusiveUIExtension extension) {
		PluginServices.exclusiveUIExtension = extension;
	}

	public static void addLoaders(ArrayList<ClassLoader> classLoaders) {
		PluginClassLoader.addLoaders(classLoaders);
	}

	public static IconThemeManager getIconThemeManager() {
		return IconThemeManager.getIconThemeManager();
	}

	public static IIconTheme getIconTheme() {
		return IconThemeManager.getIconThemeManager().getCurrent();
	}

	/**
	 * Try to detect if the application is running in a development environment. <br>
	 * This look for <b>.project</b> and <b>.classpath</b> files in the starts
	 * path of the application.
	 * 
	 * @return true if <b>.project</b> and <b>.classpath</b> are in the
	 *         development path
	 */
	public static boolean runningInDevelopment() {
		String andamiPath;
		Properties props = System.getProperties();

		try {
			try {
				andamiPath = (new File(Launcher.class.getResource(".")
						.getFile()
						+ File.separator
						+ ".."
						+ File.separator
						+ ".." + File.separator + ".." + File.separator + ".."))
						.getCanonicalPath();
			} catch (IOException e) {
				andamiPath = (new File(Launcher.class.getResource(".")
						.getFile()
						+ File.separator
						+ ".."
						+ File.separator
						+ ".." + File.separator + ".." + File.separator + ".."))
						.getAbsolutePath();
			}
		} catch (Exception e1) {
			andamiPath = (String) props.get("user.dir");
		}

		File andamiJar = new File(andamiPath + File.separator + "andami.jar");
		if (!andamiJar.exists())
			return false;
		File projectFile = new File(andamiPath + File.separator + ".project");
		File classpathFile = new File(andamiPath + File.separator
				+ ".classpath");
		return projectFile.exists() && classpathFile.exists();

	}
}
