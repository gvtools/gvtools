/* gvSIG. Sistema de Informaci�n Geogr�fica de la Generalitat Valenciana
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
package com.iver.andami;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.iver.andami.authentication.IAuthentication;
import com.iver.andami.authentication.LoginUI;
import com.iver.andami.config.generate.Andami;
import com.iver.andami.config.generate.AndamiConfig;
import com.iver.andami.config.generate.Plugin;
import com.iver.andami.iconthemes.IIconTheme;
import com.iver.andami.iconthemes.IconThemeManager;
import com.iver.andami.messages.Messages;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.ExclusiveUIExtension;
import com.iver.andami.plugins.ExtensionDecorator;
import com.iver.andami.plugins.IExtension;
import com.iver.andami.plugins.PluginClassLoader;
import com.iver.andami.plugins.config.generate.ActionTool;
import com.iver.andami.plugins.config.generate.ComboButton;
import com.iver.andami.plugins.config.generate.ComboButtonElement;
import com.iver.andami.plugins.config.generate.ComboScale;
import com.iver.andami.plugins.config.generate.Depends;
import com.iver.andami.plugins.config.generate.Extension;
import com.iver.andami.plugins.config.generate.Extensions;
import com.iver.andami.plugins.config.generate.LabelSet;
import com.iver.andami.plugins.config.generate.Menu;
import com.iver.andami.plugins.config.generate.PluginConfig;
import com.iver.andami.plugins.config.generate.PopupMenu;
import com.iver.andami.plugins.config.generate.PopupMenus;
import com.iver.andami.plugins.config.generate.SelectableTool;
import com.iver.andami.plugins.config.generate.SkinExtension;
import com.iver.andami.plugins.config.generate.SkinExtensionType;
import com.iver.andami.plugins.config.generate.ToolBar;
import com.iver.andami.plugins.status.IExtensionStatus;
import com.iver.andami.plugins.status.IUnsavedData;
import com.iver.andami.ui.AndamiEventQueue;
import com.iver.andami.ui.MDIManagerLoadException;
import com.iver.andami.ui.fonts.FontUtils;
import com.iver.andami.ui.mdiFrame.MDIFrame;
import com.iver.andami.ui.mdiFrame.NewStatusBar;
import com.iver.andami.ui.mdiManager.MDIManagerFactory;
import com.iver.andami.ui.splash.MultiSplashWindow;
import com.iver.andami.ui.theme.Theme;
import com.iver.andami.ui.wizard.UnsavedDataPanel;
import com.iver.utiles.DateTime;
import com.iver.utiles.FileUtils;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.xml.XMLEncodingUtils;
import com.iver.utiles.xmlEntity.generate.XmlTag;

/**
 * <p>
 * Andami's launching class. This is the class used to create the Andami's
 * plugin environment.<br>
 * </p>
 * 
 * <p>
 * <b>Syntax:</b> <br>
 * java [-Xmx512M (for 512MB of RAM)] [-classpath={a colon-separated(unix) or
 * semicolon-separated(windows) list of files containg base library of classes}]
 * [-Djava.library.path=PATH_TO_NATIVE_LIBRARIES]
 * PATH_TO_APPLICATION_HOME_DIRECTORY PATH_TO_APPLICATION_PLUGINS_DIRECTORY
 * [{list of additional custom application arguments separated by spaces}]
 * </p>
 * 
 * 
 * @author $author$
 * @version $Revision: 33300 $
 */
public class Launcher {
	private static Logger logger = Logger.getLogger(Launcher.class.getName());
	private static Preferences prefs = Preferences.userRoot().node(
			"gvsig.connection");
	private static AndamiConfig andamiConfig;
	private static MultiSplashWindow splashWindow;
	private static String appName;
	private static Locale locale;
	private static HashMap<String, PluginConfig> pluginsConfig = new HashMap<String, PluginConfig>();
	private static HashMap<String, PluginServices> pluginsServices = new HashMap<String, PluginServices>();
	private static MDIFrame frame;
	private static HashMap<Class<?>, ExtensionDecorator> classesExtensions = new HashMap<Class<?>, ExtensionDecorator>();
	private static String andamiConfigPath;
	private static String pluginsPersistencePath;
	private static final String nonWinDefaultLookAndFeel = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";

	private static ArrayList<String> pluginsOrdered = new ArrayList<String>();
	private static ArrayList<IExtension> extensions = new ArrayList<IExtension>();
	private static String appHomeDir = null;
	// it seems castor uses this encoding
	private static final String CASTORENCODING = "UTF8";

	private static final class ProxyAuth extends Authenticator {
		private PasswordAuthentication auth;

		private ProxyAuth(String user, String pass) {
			auth = new PasswordAuthentication(user, pass.toCharArray());
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return auth;
		}
	}

	public static void main(String[] args) throws Exception {
		try {

			if (!validJVM()) {
				System.exit(-1);
			}

			if (args.length < 1) {
				System.err
						.println("Uso: Launcher appName plugins-directory [language=locale]");
			}

			// Clean temporal files
			Utilities.cleanUpTempFiles();

			appName = args[0];

			appHomeDir = System.getProperty(args[0] + ".home");
			if (appHomeDir == null)
				appHomeDir = System.getProperty("user.home");

			appHomeDir += File.separator + args[0] + File.separator;

			// If gvSIG.confDir exists, then it will override any other setting
			// for
			// the configuration file path.
			// This is a Java property, which means it has to be passed to the
			// VM like this: java -DgvSIG.confDir=<path>
			String gvsig_conf_dir = System.getProperty("gvSIG.confDir");
			if (gvsig_conf_dir != null) {
				gvsig_conf_dir = gvsig_conf_dir.trim();
				if (gvsig_conf_dir.length() > 0) {
					if (gvsig_conf_dir.endsWith(File.separator)) {
						appHomeDir = gvsig_conf_dir;
					} else {
						appHomeDir = gvsig_conf_dir + File.separator;
					}
				}
			}

			FileUtils.setAppHomeDir(appHomeDir);
			logger.debug("User settings will be stored in: " + appHomeDir);

			File parent = new File(appHomeDir);
			parent.mkdirs();

			andamiConfigPath = appHomeDir + "andami-config.xml";
			pluginsPersistencePath = appHomeDir + "plugins-persistence.xml";

			// Configurar el log4j
			Launcher.class.getClassLoader().getResource(".");
			PropertyConfigurator.configure("log4j.properties");

			PatternLayout l = new PatternLayout("%p %t %C - %m%n");

			RollingFileAppender fa = new RollingFileAppender(l, appHomeDir
					+ args[0] + ".log", false);

			fa.setMaxFileSize("512KB");
			fa.setMaxBackupIndex(3);
			Logger.getRootLogger().addAppender(fa);

			// Leer el fichero de configuraci�n de andami (andami-config.xsd)
			// locale
			// Buscar actualizaci�nes al comenzar
			// Andami
			// Plugins
			// Directorio de las extensiones
			andamiConfigFromXML(andamiConfigPath);
			andamiConfig.setPluginsDirectory(args[1]);

			// Hacemos visibles los argumentos como una propiedad est�tica
			// de plugin services para quien lo quiera usar (por ejemplo, para
			// cargar un proyecto por l�nea de comandos)
			PluginServices.setArguments(args);

			configureLocales(args);

			// Se pone el lookAndFeel
			try {
				String lookAndFeel = getAndamiConfig().getLookAndFeel();
				if (lookAndFeel == null)
					lookAndFeel = getDefaultLookAndFeel();
				UIManager.setLookAndFeel(lookAndFeel);
			} catch (Exception e) {
				logger.warn(Messages.getString("Launcher.look_and_feel"), e);
			}
			FontUtils.initFonts();

			// Solucionamos el problema de permisos que se produc�a con Java
			// Web Start con este c�digo.
			// System.setSecurityManager(null);
			Policy.setPolicy(new Policy() {
				public PermissionCollection getPermissions(CodeSource codesource) {
					Permissions perms = new Permissions();
					perms.add(new AllPermission());
					return (perms);
				}

				public void refresh() {
				}
			});

			initIconThemes();
			// Registramos los iconos base
			registerIcons();
			validate();

			// Obtener la personalizaci�n de la aplicaci�n.
			Theme theme = getTheme();

			// Mostrar la ventana de inicio
			Frame f = new Frame();
			splashWindow = new MultiSplashWindow(f, theme, 190);

			// 1. Ponemos los datos del proxy
			splashWindow.process(10, PluginServices.getText(Launcher.class,
					"SplashWindow.configuring_proxy"));
			configureProxy();

			// 3. Se leen los config.xml de los plugins -----++++
			splashWindow.process(30, PluginServices.getText(Launcher.class,
					"SplashWindow.reading_plugins_config.xml"));
			loadPlugins(andamiConfig.getPluginsDirectory());

			// 4. Se configura el classloader del plugin
			splashWindow.process(40, PluginServices.getText(Launcher.class,
					"SplashWindow.setting_up_class_loaders"));
			pluginsClassLoaders();

			// 5. Se carga un Skin si alguno de los plugins trae informaci�n
			// para ello
			splashWindow.process(50, PluginServices.getText(Launcher.class,
					"SplashWindow.looking_for_a_skin"));
			skinPlugin(null);

			// 6. Se configura la cola de eventos
			splashWindow.process(60, PluginServices.getText(Launcher.class,
					"setting_up_event_queue"));
			EventQueue waitQueue = new AndamiEventQueue();
			Toolkit.getDefaultToolkit().getSystemEventQueue().push(waitQueue);

			// 7. Se configura la mensajer�a del plugin
			splashWindow
					.process(
							70,
							PluginServices
									.getText(Launcher.class,
											"SplashWindow.starting_plugin_internationalization_system"));
			pluginsMessages();

			// 8. Se modifica el andami-config con los plugins nuevos
			splashWindow.process(80, PluginServices.getText(Launcher.class,
					"SplashWindow.looking_for_a_skin"));
			updateAndamiConfig();

			frame = new MDIFrame();
			// 9. Se configura el nombre e icono de la aplicaci�n
			splashWindow.process(90, PluginServices.getText(Launcher.class,
					"SplashWindow.setting_up_applications_name_and_icons"));
			frameIcon(theme);

			// 10. Se prepara el MainFrame para albergar las extensiones
			splashWindow.process(100, PluginServices.getText(Launcher.class,
					"SplashWindow.preparing_workbench"));
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);

			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					frame.init();
				}
			});

			// 11. Leer el fichero de persistencia
			// info de los plugins
			// bookmarks de los plugins
			splashWindow.process(110, PluginServices.getText(Launcher.class,
					"SplashWindow.loading_plugin_settings"));
			loadPluginsPersistence();

			// Se instalan los controles del skin
			// 12. Se inicializan todas las extensiones de todos los plugins
			splashWindow.process(120, PluginServices.getText(Launcher.class,
					"SplashWindow.initializing_extensions"));
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					initializeExtensions();
				}
			});

			// 13. Se inicializan la extensi�n exclusiva
			splashWindow.process(130, PluginServices.getText(Launcher.class,
					"SplashWindow.setting_up_master_extension"));
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					initializeExclusiveUIExtension();
				}
			});
			frame.setClassesExtensions(classesExtensions);

			// 14. Se instalan los controles de las extensiones de los plugins
			splashWindow.process(140, PluginServices.getText(Launcher.class,
					"SplashWindow.installing_extensions_controls"));
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					installPluginsControls();

				}
			});

			// 15. Se instalan los menus de las extensiones de los plugins
			splashWindow.process(150, PluginServices.getText(Launcher.class,
					"SplashWindow.installing_extensions_menus"));
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					installPluginsMenus();

				}
			});

			// 16. Se instalan las etiquetas de las extensiones de los plugins
			splashWindow.process(160, PluginServices.getText(Launcher.class,
					"SplashWindow.installing_extensions_labels"));
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					installPluginsLabels();

				}
			});

			// 17. Se instalan los bookmarks de los plugins

			// 18. Se muestra el frame principal
			splashWindow.process(180, PluginServices.getText(Launcher.class,
					"creating_main_window"));
			frame.setVisible(true);

			// 19. Se ejecuta el postInitialize
			splashWindow.process(190, PluginServices.getText(Launcher.class,
					"SplashWindow.post_initializing_extensions"));
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					postInitializeExtensions();

				}
			});

			// Definimos un KeyEventDispatcher global para que las extensiones
			// puedan registrar sus "teclas r�pidas".
			GlobalKeyEventDispatcher keyDispatcher = GlobalKeyEventDispatcher
					.getInstance();
			KeyboardFocusManager.getCurrentKeyboardFocusManager()
					.addKeyEventDispatcher(keyDispatcher);

			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					frame.enableControls();
				}
			});
			splashWindow.close();
		} catch (Exception e) {
			logger.error("excepci�n al arrancar", e);
			System.exit(-1);
		}

	}

	private static void registerIcons() {
		PluginServices.getIconTheme().registerDefault(
				"login-gvsig",
				LoginUI.class.getClassLoader().getResource(
						"images/login_gvsig.png"));
		PluginServices.getIconTheme().registerDefault(
				"splash-gvsig",
				MultiSplashWindow.class.getClassLoader().getResource(
						"images/splash.png"));
		PluginServices.getIconTheme().registerDefault(
				"info-icon",
				NewStatusBar.class.getClassLoader().getResource(
						"images/info.gif"));
		PluginServices.getIconTheme().registerDefault(
				"error-icon",
				NewStatusBar.class.getClassLoader().getResource(
						"images/error.gif"));
		PluginServices.getIconTheme().registerDefault(
				"warning-icon",
				NewStatusBar.class.getClassLoader().getResource(
						"images/warning.gif"));
		PluginServices.getIconTheme().registerDefault(
				"no-icon",
				NewStatusBar.class.getClassLoader().getResource(
						"images/no_icon.png"));
	}

	/**
	 * Obtiene la personalizaci�n de los iconos, splash, fondo y el nombre de la
	 * aplicaci�n.
	 * 
	 * @return Theme
	 */
	private static Theme getTheme() {
		Theme theme = new Theme();
		String name = PluginServices.getArgumentByName("andamiTheme");
		// File file=new File("theme/andami-theme.xml");
		File file;
		if (name == null) {
			file = new File("theme/andami-theme.xml");
		} else {
			file = new File(name);
		}

		if (file.exists()) {
			theme.readTheme(file);
		}
		return theme;
	}

	/**
	 * Establece los datos que ten�amos guardados respecto de la configuraci�n
	 * del proxy.
	 */
	private static void configureProxy() {
		String host = prefs.get("firewall.http.host", "");
		String port = prefs.get("firewall.http.port", "");

		System.getProperties().put("http.proxyHost", host);
		System.getProperties().put("http.proxyPort", port);

		// Ponemos el usuario y clave del proxy, si existe
		String proxyUser = prefs.get("firewall.http.user", null);
		String proxyPassword = prefs.get("firewall.http.password", null);
		if (proxyUser != null) {
			System.getProperties().put("http.proxyUserName", proxyUser);
			System.getProperties().put("http.proxyPassword", proxyPassword);

			Authenticator.setDefault(new ProxyAuth(proxyUser, proxyPassword));
		} else {
			Authenticator.setDefault(new ProxyAuth("", ""));
		}
	}

	/**
	 * Recupera la geometr�a (tama�o, posic�n y estado) de la ventana principal
	 * de Andami. TODO Pendiente de ver como se asigna un pluginServices para el
	 * launcher.
	 * 
	 * @author LWS
	 */
	private static void restoreMDIStatus(XMLEntity xml) {
		if (xml == null)
			xml = new XMLEntity();
		// restore frame size
		Dimension sz = new Dimension(700, 580);
		if (xml.contains("MDIFrameSize")) {
			int[] wh = xml.getIntArrayProperty("MDIFrameSize");
			sz = new Dimension(wh[0], wh[1]);
		}
		frame.setSize(sz);
		// restore frame location
		Point pos = new Point(10, 10);
		if (xml.contains("MDIFramePos")) {
			int[] xy = xml.getIntArrayProperty("MDIFramePos");
			pos = new Point(xy[0], xy[1]);
		}
		frame.setLocation(pos);

		// restore frame status (Maximized, minimized, etc);
		int state = java.awt.Frame.MAXIMIZED_BOTH;
		if (xml.contains("MDIFrameState")) {
			state = xml.getIntProperty("MDIFrameState");
		}
		frame.setExtendedState(state);
	}

	private static XMLEntity saveMDIStatus() {
		XMLEntity xml = new XMLEntity();
		// save frame size
		int[] wh = new int[2];
		wh[0] = frame.getWidth();
		wh[1] = frame.getHeight();
		xml.putProperty("MDIFrameSize", wh);
		// save frame location
		int[] xy = new int[2];
		xy[0] = frame.getX();
		xy[1] = frame.getY();
		xml.putProperty("MDIFramePos", xy);
		// save frame status
		xml.putProperty("MDIFrameState", frame.getExtendedState());
		return xml;
	}

	private static boolean validJVM() {
		char thirdCharacter = System.getProperty("java.version").charAt(2);
		if (thirdCharacter < '4') {
			return false;
		} else {
			return true;
		}
	}

	private static void loadPluginsPersistence() throws ConfigurationException {
		XMLEntity entity = persistenceFromXML();

		for (int i = 0; i < entity.getChildrenCount(); i++) {
			XMLEntity plugin = entity.getChild(i);
			String pName = plugin
					.getStringProperty("com.iver.andami.pluginName");
			if (pluginsServices.get(pName) != null) {
				pluginsServices.get(pName).setPersistentXML(plugin);
			} else {
				if (pName.startsWith("Andami.Launcher"))
					restoreMDIStatus(plugin);
			}
		}
	}

	/**
	 * Salva la persistencia de los plugins.
	 * 
	 * @author LWS
	 */
	private static void savePluginPersistence() {
		Iterator<String> i = pluginsConfig.keySet().iterator();

		XMLEntity entity = new XMLEntity();

		while (i.hasNext()) {
			String pName = i.next();
			PluginServices ps = pluginsServices.get(pName);
			XMLEntity ent = ps.getPersistentXML();

			if (ent != null) {
				ent.putProperty("com.iver.andami.pluginName", pName);
				entity.addChild(ent);
			}
		}
		XMLEntity ent = saveMDIStatus();
		if (ent != null) {
			ent.putProperty("com.iver.andami.pluginName", "Andami.Launcher");
			entity.addChild(ent);
		}
		try {
			persistenceToXML(entity);
		} catch (ConfigurationException e1) {
			logger.error(
					Messages.getString("Launcher.Se_produjo_un_error_guardando_la_configuracion_de_los_plugins"),
					e1);
		}
	}

	private static void installPluginsLabels() {
		Iterator<String> i = pluginsConfig.keySet().iterator();

		while (i.hasNext()) {
			String name = i.next();
			PluginConfig pc = pluginsConfig.get(name);
			PluginServices ps = pluginsServices.get(name);

			LabelSet[] ls = pc.getLabelSet();

			for (int j = 0; j < ls.length; j++) {
				PluginClassLoader loader = ps.getClassLoader();

				try {
					Class<?> clase = loader.loadClass(ls[j].getClassName());
					frame.setStatusBarLabels(clase, ls[j].getLabel());
				} catch (ClassNotFoundException e) {
					logger.error(Messages.getString("Launcher.labelset_class"),
							e);
				}
			}
		}
	}

	private static String configureSkin(XMLEntity xml, String defaultSkin) {
		if (defaultSkin == null) {
			for (int i = 0; i < xml.getChildrenCount(); i++) {
				if (xml.getChild(i).contains("Skin-Selected")) {
					String className = xml.getChild(i).getStringProperty(
							"Skin-Selected");
					return className;
				}
			}
		}
		return defaultSkin;
	}

	private static void fixSkin(SkinExtension skinExtension,
			PluginClassLoader pluginClassLoader) throws MDIManagerLoadException {
		// now insert the skin selected.
		MDIManagerFactory.setSkinExtension(skinExtension, pluginClassLoader);
		// MDIManagerFactory.setSkinExtension(se,
		// ps.getClassLoader());

		Class<?> skinClass;

		try {
			skinClass = pluginClassLoader.loadClass(skinExtension
					.getClassName());

			com.iver.andami.plugins.IExtension skinInstance = (com.iver.andami.plugins.IExtension) skinClass
					.newInstance();
			// classesExtensions.put(skinClass, skinInstance);
			// jaume
			ExtensionDecorator newExtensionDecorator = new ExtensionDecorator(
					skinInstance, ExtensionDecorator.INACTIVE);
			classesExtensions.put(skinClass, newExtensionDecorator);
		} catch (ClassNotFoundException e) {
			logger.error(Messages
					.getString("Launcher.No_se_encontro_la_clase_mdi_manager"),
					e);
			throw new MDIManagerLoadException(e);
		} catch (InstantiationException e) {
			logger.error(
					Messages.getString("Launcher.No_se_pudo_instanciar_la_clase_mdi_manager"),
					e);
			throw new MDIManagerLoadException(e);
		} catch (IllegalAccessException e) {
			logger.error(
					Messages.getString("Launcher.No_se_pudo_acceder_a_la_clase_mdi_manager"),
					e);
			throw new MDIManagerLoadException(e);
		}

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @throws MDIManagerLoadException
	 */
	private static void skinPlugin(String defaultSkin)
			throws MDIManagerLoadException {
		XMLEntity entity = null;
		try {
			entity = persistenceFromXML();
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Iterator<String> i = pluginsConfig.keySet().iterator();

		SkinExtension skinExtension = null;
		PluginClassLoader pluginClassLoader = null;
		ArrayList<SkinExtension> skinExtensions = new ArrayList<SkinExtension>();
		while (i.hasNext()) {
			String name = i.next();
			PluginConfig pc = pluginsConfig.get(name);
			PluginServices ps = pluginsServices.get(name);

			if (pc.getExtensions().getSkinExtension() != null) {
				// if (MDIManagerFactory.getSkinExtension() != null) {
				// logger.warn(Messages.getString(
				// "Launcher.Dos_skin_extension"));
				// }

				SkinExtension[] se = pc.getExtensions().getSkinExtension();
				for (int numExten = 0; numExten < se.length; numExten++) {
					skinExtensions.add(se[numExten]);
				}
				for (int j = 0; j < se.length; j++) {
					String configuredSkin = Launcher.configureSkin(entity,
							defaultSkin);
					if (configuredSkin != null
							&& configuredSkin.equals(se[j].getClassName())) {
						skinExtension = se[j];
						pluginClassLoader = ps.getClassLoader();
					}
				}
			}
		}

		if ((skinExtension != null) && (pluginClassLoader != null)) {
			// configured skin was found
			fixSkin(skinExtension, pluginClassLoader);
		} else {
			if (skinExtensions.contains("com.iver.core.mdiManager.NewSkin")) {
				// try first NewSkin (from CorePlugin)
				skinPlugin("com.iver.core.mdiManager.NewSkin");
			} else if (skinExtensions.size() > 0) {
				// try to load the first skin found
				skinPlugin(skinExtensions.get(0).getClassName());
			} else {
				throw new MDIManagerLoadException("No Skin-Extension installed");
			}
		}

	}

	private static void frameIcon(Theme theme) {
		Iterator<String> i = pluginsConfig.keySet().iterator();

		while (i.hasNext()) {
			String pName = i.next();
			PluginConfig pc = pluginsConfig.get(pName);
			if (pc.getIcon() != null) {
				if (theme.getIcon() != null) {
					frame.setIconImage(theme.getIcon().getImage());
				} else {

					ImageIcon icon = PluginServices.getIconTheme().get(
							pc.getIcon().getSrc());
					frame.setIconImage(icon.getImage());

				}
				if (theme.getName() != null) {
					frame.setTitlePrefix(theme.getName());
				} else {
					frame.setTitlePrefix(pc.getIcon().getText());
				}
				if (theme.getBackgroundImage() != null) {

					PluginServices.getMDIManager().setBackgroundImage(
							theme.getBackgroundImage(), theme.getTypeDesktop());
				}
			}
		}
	}

	private static void initializeExtensions() {
		Iterator<String> i = pluginsOrdered.iterator();

		while (i.hasNext()) {
			String pName = i.next();
			logger.debug("Initializing extensions from " + pName);
			PluginConfig pc = pluginsConfig.get(pName);
			PluginServices ps = pluginsServices.get(pName);

			Extension[] exts = pc.getExtensions().getExtension();

			TreeMap<Extension, Object> orderedExtensions = new TreeMap<Extension, Object>(
					new ExtensionComparator());

			for (int j = 0; j < exts.length; j++) {
				if (!exts[j].getActive()) {
					continue;
				}

				if (orderedExtensions.containsKey(exts[j])) {
					logger.warn(Messages
							.getString("Launcher.Two_extensions_with_the_same_priority")
							+ exts[j].getClassName());
				}

				orderedExtensions.put(exts[j], null);
			}

			Iterator<Extension> e = orderedExtensions.keySet().iterator();

			while (e.hasNext()) {
				Extension extension = e.next();
				IExtension extensionInstance;

				try {
					Class<?> extensionClass = ps.getClassLoader().loadClass(
							extension.getClassName());
					extensionInstance = (IExtension) extensionClass
							.newInstance();

					// CON DECORATOR
					// ANTES: classesExtensions.put(extensionClass,
					// extensionInstance);
					// AHORA: CREAMOS UNA ExtensionDecorator y asignamos esta
					// instancia para
					// poder ampliar con nuevas propiedades (AlwaysVisible, por
					// ejemplo)
					// Para crear la nueva clase ExtensionDecorator, le pasamos
					// como par�metro
					// la extensi�n original que acabamos de crear
					// 0-> Inactivo, controla la extension
					// 1-> Siempre visible
					// 2-> Invisible
					ExtensionDecorator newExtensionDecorator = new ExtensionDecorator(
							extensionInstance, ExtensionDecorator.INACTIVE);
					classesExtensions
							.put(extensionClass, newExtensionDecorator);
					logger.info("Initializing " + extension.getClassName()
							+ "...");
					// logger.debug("Initializing " + extension.getClassName());
					extensionInstance.initialize();
					extensions.add(extensionInstance);
					// logger.debug(extension.getClassName() + " initialized.");

				} catch (InstantiationException e1) {
					logger.error(
							Messages.getString("Launcher.Error_instanciando_la_extension")
									+ extension.getClassName(), e1);
				} catch (IllegalAccessException e1) {
					logger.error(
							Messages.getString("Launcher.Error_instanciando_la_extension")
									+ extension.getClassName(), e1);
				} catch (ClassNotFoundException e1) {
					logger.error(
							Messages.getString("Launcher.No_se_encontro_la_clase_de_la_extension")
									+ extension.getClassName(), e1);
				} catch (NoClassDefFoundError e1) {
					logger.error(
							Messages.getString("Launcher.Error_localizando_la_clase_de_la_extension")
									+ extension.getClassName(), e1);
				}
			}
		}
	}

	private static void postInitializeExtensions() {
		for (int i = 0; i < extensions.size(); i++) {
			extensions.get(i).postInitialize();
		}
	}

	private static void installPluginsMenus() {
		TreeMap<SortableMenu, Object> orderedMenus = new TreeMap<SortableMenu, Object>(
				new MenuComparator());

		Iterator<String> i = pluginsConfig.keySet().iterator();

		while (i.hasNext()) {
			String pName = i.next();
			PluginServices ps = pluginsServices.get(pName);
			PluginConfig pc = pluginsConfig.get(pName);

			Extension[] exts = pc.getExtensions().getExtension();

			for (int j = 0; j < exts.length; j++) {
				if (!exts[j].getActive()) {
					continue;
				}

				Menu[] menus = exts[j].getMenu();

				for (int k = 0; k < menus.length; k++) {
					SortableMenu sm = new SortableMenu(ps.getClassLoader(),
							exts[j], menus[k]);

					if (orderedMenus.containsKey(sm)) {
						logger.error(Messages
								.getString("Launcher.Two_menus_with_the_same_position")
								+ " - "
								+ menus[k].getText()
								+ " - "
								+ exts[j].getClassName());
					}

					orderedMenus.put(sm, null);
				}
			}

			// Se instalan las extensiones de MDI
			SkinExtension[] skinExts = pc.getExtensions().getSkinExtension();
			for (int j = 0; j < skinExts.length; j++) {

				if (skinExts[j] != null) {
					Menu[] menu = skinExts[j].getMenu();

					for (int k = 0; k < menu.length; k++) {
						SortableMenu sm = new SortableMenu(ps.getClassLoader(),
								skinExts[j], menu[k]);

						if (orderedMenus.containsKey(sm)) {
							logger.error(Messages
									.getString("Launcher.Two_menus_with_the_same_position")
									+ skinExts[j].getClassName());
						}

						orderedMenus.put(sm, null);
					}
				}
			}
		}

		// Se itera por los menus ordenados
		Iterator<SortableMenu> e = orderedMenus.keySet().iterator();

		// Se ordenan los menues
		while (e.hasNext()) {
			try {
				SortableMenu sm = e.next();
				frame.addMenu(sm.loader, sm.extension, sm.menu);
			} catch (ClassNotFoundException ex) {
				logger.error(
						Messages.getString("Launcher.No_se_encontro_la_clase_de_la_extension"),
						ex);
			}
		}
	}

	/**
	 * Installs the menus, toolbars, actiontools, selectable toolbars and
	 * combos. The order in which they are shown is determined here.
	 */
	private static void installPluginsControls() {
		Iterator<String> i = pluginsConfig.keySet().iterator();

		HashMap<Extension, PluginServices> extensionPluginServices = new HashMap<Extension, PluginServices>();
		HashMap<Extension, PluginConfig> extensionPluginConfig = new HashMap<Extension, PluginConfig>();
		TreeMap<Extension, Object> orderedExtensions = new TreeMap<Extension, Object>(
				new ExtensionComparator());

		// First of all, sort the extensions.
		// We need to iterate on the plugins, and iterate on each plugin's
		// extensions
		// (each plugin may contain one or more extensions)
		while (i.hasNext()) { // iterate on the plugins
			String pName = i.next();
			PluginConfig pc = pluginsConfig.get(pName);
			PluginServices ps = pluginsServices.get(pName);

			Extension[] exts = pc.getExtensions().getExtension();

			for (int j = 0; j < exts.length; j++) { // iterate on the extensions
				if (exts[j].getActive()) {
					if (orderedExtensions.containsKey(exts[j])) {
						logger.error(Messages
								.getString("Launcher.Two_extensions_with_the_same_priority")
								+ exts[j].getClassName());
					}

					orderedExtensions.put(exts[j], null);
					extensionPluginServices.put(exts[j], ps);
					extensionPluginConfig.put(exts[j], pc);
				}
			}
		}

		TreeMap<SortableTool, Object> orderedTools = new TreeMap<SortableTool, Object>(
				new ToolComparator());
		Iterator<Extension> e = orderedExtensions.keySet().iterator();

		// sort the toolbars and tools from 'normal' extensions (actiontools,
		// selectabletools)
		// and load the combo-scales and combo-buttons for the status bar
		while (e.hasNext()) {
			Extension ext = e.next();

			ToolBar[] toolbars = ext.getToolBar();

			// get tools from toolbars
			for (int k = 0; k < toolbars.length; k++) {
				ActionTool[] tools = toolbars[k].getActionTool();

				for (int t = 0; t < tools.length; t++) {
					SortableTool sm = new SortableTool(extensionPluginServices
							.get(ext).getClassLoader(), ext, toolbars[k],
							tools[t]);
					orderedTools.put(sm, null);
				}

				SelectableTool[] sTools = toolbars[k].getSelectableTool();

				for (int t = 0; t < sTools.length; t++) {
					SortableTool sm = new SortableTool(extensionPluginServices
							.get(ext).getClassLoader(), ext, toolbars[k],
							sTools[t]);
					orderedTools.put(sm, null);
				}
			}

			// get controls for statusBar
			PluginServices ps = extensionPluginServices.get(ext);
			PluginClassLoader loader = ps.getClassLoader();

			// ArrayList componentList = new ArrayList();
			ComboScale[] comboScaleArray = ext.getComboScale();
			for (int k = 0; k < comboScaleArray.length; k++) {
				org.gvsig.gui.beans.controls.comboscale.ComboScale combo = new org.gvsig.gui.beans.controls.comboscale.ComboScale();
				String label = comboScaleArray[k].getLabel();
				if (label != null)
					combo.setLabel(label);
				String name = comboScaleArray[k].getName();
				if (name != null)
					combo.setName(name);
				String[] elementsString = ((String) comboScaleArray[k]
						.getElements()).split(";");
				long[] elements = new long[elementsString.length];
				for (int currentElem = 0; currentElem < elementsString.length; currentElem++) {
					try {
						elements[currentElem] = Long
								.parseLong(elementsString[currentElem]);
					} catch (NumberFormatException nfex1) {
						logger.error(ext.getClassName()
								+ " -- "
								+ Messages
										.getString("error_parsing_comboscale_elements"));
						elements[currentElem] = 0;
					}
				}
				combo.setItems(elements);
				try {
					long value = Long.parseLong((String) comboScaleArray[k]
							.getValue());
					combo.setScale(value);
				} catch (NumberFormatException nfex2) {
					logger.error(ext.getClassName()
							+ " -- "
							+ Messages
									.getString("error_parsing_comboscale_value"));
				}
				try {
					frame.addStatusBarControl(
							loader.loadClass(ext.getClassName()), combo);
				} catch (ClassNotFoundException e1) {
					logger.error(
							Messages.getString("Launcher.error_getting_class_loader_for_status_bar_control"),
							e1);
				}
			}

			ComboButton[] comboButtonArray = ext.getComboButton();
			for (int k = 0; k < comboButtonArray.length; k++) {
				ComboButtonElement[] elementList = comboButtonArray[k]
						.getComboButtonElement();
				org.gvsig.gui.beans.controls.combobutton.ComboButton combo = new org.gvsig.gui.beans.controls.combobutton.ComboButton();
				String name = comboButtonArray[k].getName();
				if (name != null)
					combo.setName(name);
				for (int currentElement = 0; currentElement < elementList.length; currentElement++) {
					ComboButtonElement element = elementList[currentElement];
					ImageIcon icon;
					URL iconLocation = loader.getResource(element.getIcon());
					if (iconLocation == null)
						logger.error(Messages.getString("Icon_not_found_")
								+ element.getIcon());
					else {
						icon = new ImageIcon(iconLocation);
						JButton button = new JButton(icon);
						combo.addButton(button);
						button.setActionCommand(element.getActionCommand());
					}
				}
				try {
					frame.addStatusBarControl(
							loader.loadClass(ext.getClassName()), combo);
				} catch (ClassNotFoundException e1) {
					logger.error(
							Messages.getString("Launcher.error_getting_class_loader_for_status_bar_control"),
							e1);
				}
			}
		}

		// Add the tools from MDI extensions to the ordered tool-list, so that
		// we get a sorted list containing all the tools
		i = pluginsConfig.keySet().iterator();
		while (i.hasNext()) {
			String pName = i.next();
			PluginConfig pc = pluginsConfig.get(pName);
			PluginServices ps = pluginsServices.get(pName);

			SkinExtension[] skinExts = pc.getExtensions().getSkinExtension();
			for (int j = 0; j < skinExts.length; j++) {

				if (skinExts[j] != null) {
					ToolBar[] toolbars = skinExts[j].getToolBar();

					for (int k = 0; k < toolbars.length; k++) {
						ActionTool[] tools = toolbars[k].getActionTool();

						for (int t = 0; t < tools.length; t++) {
							SortableTool stb = new SortableTool(
									ps.getClassLoader(), skinExts[j],
									toolbars[k], tools[t]);
							orderedTools.put(stb, null);
						}

						SelectableTool[] sTools = toolbars[k]
								.getSelectableTool();

						for (int t = 0; t < sTools.length; t++) {
							SortableTool stb = new SortableTool(
									ps.getClassLoader(), skinExts[j],
									toolbars[k], sTools[t]);
							orderedTools.put(stb, null);
						}
					}
				}
			}
			// Install popup menus
			PopupMenus pus = pc.getPopupMenus();

			if (pus != null) {
				PopupMenu[] menus = pus.getPopupMenu();

				for (int j = 0; j < menus.length; j++) {
					frame.addPopupMenu(ps.getClassLoader(), menus[j]);
				}
			}
		}

		// loop on the ordered extension list, to add them to the interface in
		// an ordered way
		Iterator<SortableTool> t = orderedTools.keySet().iterator();
		while (t.hasNext()) {
			try {
				SortableTool stb = t.next();
				if (stb.actiontool != null)
					frame.addTool(stb.loader, stb.extension, stb.toolbar,
							stb.actiontool);
				else
					frame.addTool(stb.loader, stb.extension, stb.toolbar,
							stb.selectabletool);
			} catch (ClassNotFoundException ex) {
				logger.error(
						Messages.getString("Launcher.No_se_encontro_la_clase_de_la_extension"),
						ex);
			}
		}
	}

	/**
	 * Adds new plugins to the the andami-config file.
	 */
	private static void updateAndamiConfig() {
		HashSet<String> olds = new HashSet<String>();

		Plugin[] plugins = andamiConfig.getPlugin();

		for (int i = 0; i < plugins.length; i++) {
			olds.add(plugins[i].getName());
		}

		Iterator<PluginServices> i = pluginsServices.values().iterator();

		while (i.hasNext()) {
			PluginServices ps = i.next();

			if (!olds.contains(ps.getPluginName())) {
				Plugin p = new Plugin();
				p.setName(ps.getPluginName());
				p.setUpdate(false);

				andamiConfig.addPlugin(p);
			}
		}
	}

	private static void pluginsClassLoaders() {
		HashSet<String> instalados = new HashSet<String>();

		// Se itera hasta que est�n todos instalados
		while (instalados.size() != pluginsConfig.size()) {
			boolean circle = true;

			// Hacemos una pasada por todos los plugins
			Iterator<String> i = pluginsConfig.keySet().iterator();

			while (i.hasNext()) {
				String pluginName = i.next();
				PluginConfig config = pluginsConfig.get(pluginName);

				if (instalados.contains(pluginName)) {
					continue;
				}

				// Se obtienen las dependencias y sus class loaders
				boolean ready = true;
				Depends[] dependencies = config.getDepends();
				PluginClassLoader[] loaders = new PluginClassLoader[dependencies.length];

				for (int j = 0; j < dependencies.length; j++) {
					if (pluginsConfig.get(dependencies[j].getPluginName()) == null) {
						logger.error(Messages
								.getString("Launcher.Dependencia_no_resuelta_en_plugin")
								+ pluginName
								+ ": "
								+ dependencies[j].getPluginName());

						continue;
					}

					if (!instalados.contains(dependencies[j].getPluginName())) {
						ready = false;
					} else {
						loaders[j] = pluginsServices.get(
								dependencies[j].getPluginName())
								.getClassLoader();
					}
				}

				// Si no est�n sus dependencias satisfechas se aborta la
				// instalaci�n
				if (!ready) {
					continue;
				}

				// Se genera el class loader
				String jardir = config.getLibraries().getLibraryDir();
				File jarDir = new File(andamiConfig.getPluginsDirectory()
						+ File.separator + pluginName + File.separator + jardir);
				File[] jarFiles = jarDir.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return (pathname.getName().toUpperCase()
								.endsWith(".JAR"))
								|| (pathname.getName().toUpperCase()
										.endsWith(".ZIP"));
					}
				});

				URL[] urls = new URL[jarFiles.length];

				for (int j = 0; j < jarFiles.length; j++) {
					try {
						urls[j] = new URL("file:" + jarFiles[j]);
					} catch (MalformedURLException e) {
						logger.error(Messages
								.getString("Launcher.No_se_puede_acceder_a")
								+ jarFiles[j]);
					}
				}

				PluginClassLoader loader;

				try {
					loader = new PluginClassLoader(urls,
							andamiConfig.getPluginsDirectory() + File.separator
									+ pluginName,
							Launcher.class.getClassLoader(), loaders);

					PluginServices ps = new PluginServices(loader);

					pluginsServices.put(ps.getPluginName(), ps);

					instalados.add(pluginName);
					// FJP: Los metemos ordenados para luego no cargar uno que
					// necesita de otro antes de tiempo. Esto lo usaremos al
					// inicializar los plugins
					pluginsOrdered.add(pluginName);

					circle = false;
				} catch (IOException e) {
					logger.error(
							Messages.getString("Launcher.Error_con_las_librerias_del_plugin"),
							e);
					pluginsConfig.remove(pluginName);
					i = pluginsConfig.keySet().iterator();
				}
			}

			if (circle) {
				logger.error(Messages
						.getString("Launcher.Hay_dependencias_circulares"));

				break;
			}
		}

		// Se eliminan los plugins que no fueron instalados
		Iterator<String> i = pluginsConfig.keySet().iterator();

		while (i.hasNext()) {
			String pluginName = i.next();
			PluginServices ps = pluginsServices.get(pluginName);

			if (ps == null) {
				pluginsConfig.remove(pluginName);
				i = pluginsConfig.keySet().iterator();
			}
		}
	}

	private static void pluginsMessages() {
		Iterator<String> iterator = pluginsOrdered.iterator();
		PluginConfig config;
		PluginServices ps;

		while (iterator.hasNext()) {
			String pluginName = iterator.next();
			config = pluginsConfig.get(pluginName);
			ps = pluginsServices.get(pluginName);

			if (config.getResourceBundle() != null
					&& !config.getResourceBundle().getName().equals("")) {
				// add the locale files associated with the plugin
				org.gvsig.i18n.Messages.addResourceFamily(config
						.getResourceBundle().getName(), ps.getClassLoader(),
						pluginName);
			}
		}
	}

	static PluginServices getPluginServices(String name) {
		return pluginsServices.get(name);
	}

	static String getPluginsDir() {
		return andamiConfig.getPluginsDirectory();
	}

	static void setPluginsDir(String s) {
		andamiConfig.setPluginsDirectory(s);
	}

	static MDIFrame getMDIFrame() {
		return frame;
	}

	private static void loadPlugins(String pluginsDirectory) {
		File pDir = new File(pluginsDirectory);

		if (!pDir.exists()) {
			logger.error("\n\tPlugins directory not found: "
					+ pDir.getAbsolutePath()
					+ "\n\tDid you specify the correct directory in the Launch Configuration parameters?\n\tExiting now...");
			System.exit(-1);
			return;
		}

		File[] pluginDirs = pDir.listFiles();
		if (pluginDirs.length == 0) {
			logger.error("\n\tPlugins directory is empty: "
					+ pDir.getAbsolutePath()
					+ "Did you specify the correct directory in the Launch Configuration parameters?\n\tExiting now...");
			System.exit(-1);
			return;
		}

		for (int i = 0; i < pluginDirs.length; i++) {
			if (pluginDirs[i].isDirectory()) {
				File configXml = new File(pluginDirs[i].getAbsolutePath()
						+ File.separator + "config.xml");

				try {
					FileInputStream is = new FileInputStream(configXml);
					Reader xml = com.iver.utiles.xml.XMLEncodingUtils
							.getReader(is);
					if (xml == null) {
						// the encoding was not correctly detected, use system
						// default
						xml = new FileReader(configXml);
					} else {
						// use a buffered reader to improve performance
						xml = new BufferedReader(xml);
					}
					PluginConfig pConfig = (PluginConfig) PluginConfig
							.unmarshal(xml);
					pluginsConfig.put(pluginDirs[i].getName(), pConfig);
				} catch (FileNotFoundException e) {
					logger.info(Messages
							.getString("Launcher.Ignorando_el_directorio")
							+ pluginDirs[i].getAbsolutePath()
							+ Messages
									.getString("Launcher.config_no_encontrado"));
				} catch (MarshalException e) {
					logger.info(
							Messages.getString("Launcher.Ignorando_el_directorio")
									+ pluginDirs[i].getAbsolutePath()
									+ Messages
											.getString("Launcher.config_mal_formado"),
							e);
				} catch (ValidationException e) {
					logger.info(
							Messages.getString("Launcher.Ignorando_el_directorio")
									+ pluginDirs[i].getAbsolutePath()
									+ Messages
											.getString("Launcher.config_mal_formado"),
							e);
				}
			}
		}

		if (pluginsConfig.size() == 0) {
			logger.error("\n\tNo valid plugin was found. The plugins directory currently is: "
					+ pDir.getAbsolutePath()
					+ "\n\tDid you specify the correct directory in the Launch Configuration parameters?\n\tExiting now...");
			System.exit(-1);
			return;
		}
	}

	private static Locale getLocale(String language, String country,
			String variant) {
		if (variant != null) {
			return new Locale(language, country, variant);
		} else if (country != null) {
			return new Locale(language, country);
		} else if (language != null) {
			return new Locale(language);
		} else {
			return new Locale("es");
		}
	}

	private static void andamiConfigToXML(String file) throws IOException,
			MarshalException, ValidationException {
		// write on a temporary file in order to not destroy current file if
		// there is some problem while marshaling
		File tmpFile = new File(file + "-"
				+ DateTime.getCurrentDate().getTime());
		File xml = new File(file);
		File parent = xml.getParentFile();
		parent.mkdirs();

		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(tmpFile));
		OutputStreamWriter writer = new OutputStreamWriter(os, CASTORENCODING);
		andamiConfig.marshal(writer);
		writer.close();

		// if marshaling process finished correctly, move the file to the
		// correct one
		xml.delete();
		if (!tmpFile.renameTo(xml)) {
			// if rename was not succesful, try copying it
			FileChannel sourceChannel = new FileInputStream(tmpFile)
					.getChannel();
			FileChannel destinationChannel = new FileOutputStream(xml)
					.getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(),
					destinationChannel);
			sourceChannel.close();
			destinationChannel.close();
		}
	}

	private static void andamiConfigFromXML(String file)
			throws ConfigurationException {
		File xml = new File(file);

		InputStreamReader reader = null;
		try {
			// Se lee la configuraci�n
			reader = XMLEncodingUtils.getReader(xml);
			andamiConfig = (AndamiConfig) AndamiConfig.unmarshal(reader);
		} catch (FileNotFoundException e) {
			// Si no existe se ponen los valores por defecto
			andamiConfig = getDefaultAndamiConfig();
		} catch (MarshalException e) {
			// try to close the stream, maybe it remains open
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			// if there was a problem reading the file, backup it and create a
			// new one with default values
			String backupFile = file + "-"
					+ DateTime.getCurrentDate().getTime();
			NotificationManager
					.addError(
							Messages.getString("Error_reading_andami_config_New_file_created_A_backup_was_made_on_")
									+ backupFile, new ConfigurationException(e));
			xml.renameTo(new File(backupFile));
			andamiConfig = getDefaultAndamiConfig();
		} catch (ValidationException e) {
			throw new ConfigurationException(e);
		}
	}

	private static AndamiConfig getDefaultAndamiConfig() {
		AndamiConfig andamiConfig = new AndamiConfig();

		Andami andami = new Andami();
		andami.setUpdate(true);
		andamiConfig.setAndami(andami);
		andamiConfig.setLocaleCountry(Locale.getDefault().getCountry());
		andamiConfig.setLocaleLanguage(Locale.getDefault().getLanguage());
		andamiConfig.setLocaleVariant(Locale.getDefault().getVariant());

		if (System.getProperty("javawebstart.version") != null) // Es java web
																// start)
		{
			andamiConfig.setPluginsDirectory(new File(appHomeDir
					+ "extensiones").getAbsolutePath());
		} else {
			andamiConfig.setPluginsDirectory(new File(appName + File.separator
					+ "extensiones").getAbsolutePath());
		}

		andamiConfig.setPlugin(new Plugin[0]);
		return andamiConfig;
	}

	private static XMLEntity persistenceFromXML() throws ConfigurationException {
		File xml = new File(pluginsPersistencePath);

		if (xml.exists()) {
			InputStreamReader reader = null;

			try {
				reader = XMLEncodingUtils.getReader(xml);
				XmlTag tag = (XmlTag) XmlTag.unmarshal(reader);
				return new XMLEntity(tag);
			} catch (FileNotFoundException e) {
				throw new ConfigurationException(e);
			} catch (MarshalException e) {

				// try to reopen with default encoding (for backward
				// compatibility)
				try {
					reader = new FileReader(xml);
					XmlTag tag = (XmlTag) XmlTag.unmarshal(reader);
					return new XMLEntity(tag);

				} catch (MarshalException ex) {
					// try to close the stream, maybe it remains open
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e1) {
						}
					}
					// backup the old file
					String backupFile = pluginsPersistencePath + "-"
							+ DateTime.getCurrentDate().getTime();
					NotificationManager
							.addError(
									Messages.getString("Error_reading_plugin_persinstence_New_file_created_A_backup_was_made_on_")
											+ backupFile,
									new ConfigurationException(e));
					xml.renameTo(new File(backupFile));
					// create a new, empty configuration
					return new XMLEntity();
				} catch (FileNotFoundException ex) {
					return new XMLEntity();
				} catch (ValidationException ex) {
					throw new ConfigurationException(e);
				}
			} catch (ValidationException e) {
				throw new ConfigurationException(e);
			}
		} else {
			return new XMLEntity();
		}
	}

	private static void persistenceToXML(XMLEntity entity)
			throws ConfigurationException {
		// write on a temporary file in order to not destroy current file if
		// there is some problem while marshaling
		File tmpFile = new File(pluginsPersistencePath + "-"
				+ DateTime.getCurrentDate().getTime());

		File xml = new File(pluginsPersistencePath);
		OutputStreamWriter writer = null;

		try {
			writer = new OutputStreamWriter(new FileOutputStream(tmpFile),
					CASTORENCODING);
			entity.getXmlTag().marshal(writer);
			writer.close();

			// if marshaling process finished correctly, move the file to the
			// correct one
			xml.delete();
			if (!tmpFile.renameTo(xml)) {
				// if rename was not succesful, try copying it
				FileChannel sourceChannel = new FileInputStream(tmpFile)
						.getChannel();
				FileChannel destinationChannel = new FileOutputStream(xml)
						.getChannel();
				sourceChannel.transferTo(0, sourceChannel.size(),
						destinationChannel);
				sourceChannel.close();
				destinationChannel.close();

			}
		} catch (FileNotFoundException e) {
			throw new ConfigurationException(e);
		} catch (MarshalException e) {
			// try to close the stream, maybe it remains open
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		} catch (ValidationException e) {
			throw new ConfigurationException(e);
		} catch (IOException e) {
			throw new ConfigurationException(e);
		}
	}

	static MDIFrame getFrame() {
		return frame;
	}

	/**
	 * Gracefully closes the application. It shows dialogs to save data, finish
	 * processes, etc, then it terminates the extensions, removes temporal files
	 * and finally exits.
	 */
	public synchronized static void closeApplication() {
		TerminationProcess terminationProcess = (new Launcher()).new TerminationProcess();
		terminationProcess.run();
	}

	static HashMap<Class<?>, ExtensionDecorator> getClassesExtensions() {
		return classesExtensions;
	}

	private static Extensions[] getExtensions() {
		ArrayList<Extensions> array = new ArrayList<Extensions>();
		Iterator<PluginConfig> iter = pluginsConfig.values().iterator();

		while (iter.hasNext()) {
			array.add(iter.next().getExtensions());
		}

		return array.toArray(new Extensions[0]);
	}

	public static Iterator<IExtension> getExtensionIterator() {
		return extensions.iterator();
	}

	public static HashMap<String, PluginConfig> getPluginConfig() {
		return pluginsConfig;
	}

	public static Extension getExtension(String s) {
		Extensions[] exts = getExtensions();

		for (int i = 0; i < exts.length; i++) {
			for (int j = 0; j < exts[i].getExtensionCount(); j++) {
				if (exts[i].getExtension(j).getClassName().equals(s)) {
					return exts[i].getExtension(j);
				}
			}
		}

		return null;
	}

	public static AndamiConfig getAndamiConfig() {
		return andamiConfig;
	}

	private static class ExtensionComparator implements Comparator<Extension> {
		public int compare(Extension e1, Extension e2) {
			if (!e1.hasPriority() && !e2.hasPriority()) {
				return -1;
			}

			if (e1.hasPriority() && !e2.hasPriority()) {
				return Integer.MIN_VALUE;
			}

			if (e2.hasPriority() && !e1.hasPriority()) {
				return Integer.MAX_VALUE;
			}

			if (e1.getPriority() != e2.getPriority()) {
				return e2.getPriority() - e1.getPriority();
			} else {
				return (e2.toString().compareTo(e1.toString()));
			}
		}
	}

	private static class MenuComparator implements Comparator<SortableMenu> {
		public int compare(SortableMenu sm1, SortableMenu sm2) {
			if (!sm1.menu.hasPosition() && !sm2.menu.hasPosition()) {
				return 1;
			}

			if (sm1.menu.hasPosition() && !sm2.menu.hasPosition()) {
				return Integer.MIN_VALUE;
			}

			if (sm2.menu.hasPosition() && !sm1.menu.hasPosition()) {
				return Integer.MAX_VALUE;
			}
			if (sm1.menu.getPosition() != sm2.menu.getPosition()) {
				// we don't return 0 unless both objects are the same, otherwise
				// the objects get overwritten in the treemap
				return sm1.menu.getPosition() - sm2.menu.getPosition();
			} else {
				return (sm1.toString().compareTo(sm2.toString()));
			}
		}
	}

	private static class SortableMenu {
		public PluginClassLoader loader;
		public Menu menu;
		public SkinExtensionType extension;

		public SortableMenu(PluginClassLoader loader,
				SkinExtensionType skinExt, Menu menu2) {
			extension = skinExt;
			menu = menu2;
			this.loader = loader;
		}
	}

	private static class SortableTool {
		public PluginClassLoader loader;
		public ToolBar toolbar;
		public ActionTool actiontool;
		public SelectableTool selectabletool;
		public SkinExtensionType extension;

		public SortableTool(PluginClassLoader loader,
				SkinExtensionType skinExt, ToolBar toolbar2,
				ActionTool actiontool2) {
			extension = skinExt;
			toolbar = toolbar2;
			actiontool = actiontool2;
			this.loader = loader;
		}

		public SortableTool(PluginClassLoader loader,
				SkinExtensionType skinExt, ToolBar toolbar2,
				SelectableTool selectabletool2) {
			extension = skinExt;
			toolbar = toolbar2;
			selectabletool = selectabletool2;
			this.loader = loader;
		}
	}

	private static class ToolBarComparator implements Comparator<SortableTool> {
		public int compare(SortableTool e1, SortableTool e2) {
			// if the toolbars have the same name, they are considered to be
			// the same toolbar, so we don't need to do further comparing
			if (e1.toolbar.getName().equals(e2.toolbar.getName()))
				return 0;

			if (!e1.toolbar.hasPosition() && !e2.toolbar.hasPosition()) {
				return 1;
			}

			if (e1.toolbar.hasPosition() && !e2.toolbar.hasPosition()) {
				return Integer.MIN_VALUE;
			}

			if (e2.toolbar.hasPosition() && !e1.toolbar.hasPosition()) {
				return Integer.MAX_VALUE;
			}
			if (e1.toolbar.getPosition() != e2.toolbar.getPosition())
				return e1.toolbar.getPosition() - e2.toolbar.getPosition();

			if (e1.toolbar.getActionTool().equals(e2.toolbar.getActionTool())
					&& e1.toolbar.getSelectableTool().equals(
							e2.toolbar.getSelectableTool())) {
				return 0;
			}
			return (e1.toolbar.toString().compareTo(e2.toolbar.toString()));
		}
	}

	/**
	 * <p>
	 * This class is used to compare tools (selectabletool and actiontool),
	 * using the "position" attribute.
	 * </p>
	 * <p>
	 * The ordering criteria are:
	 * </p>
	 * <ul>
	 * <li>If the tools are placed in different toolbars, they use the toolbars'
	 * order. (using the ToolBarComparator).</li>
	 * <li></li>
	 * <li>If any of the tools has not 'position' attribute, the tool which
	 * <strong>has</strong> the attribute will be placed first.</li>
	 * <li>If both tools have the same position (or they don't have a 'position'
	 * attribute), the priority of the extensions where the tool is defined.</li>
	 * </ul>
	 * 
	 * @author cesar
	 * @version $Revision: 33300 $
	 */
	private static class ToolComparator implements Comparator<SortableTool> {
		private static ToolBarComparator toolBarComp = new ToolBarComparator();

		public int compare(SortableTool st1, SortableTool st2) {
			// compare the toolbars which contain the tools
			int result = toolBarComp.compare(st1, st2);
			if (result != 0) { // if the toolbars are different, use their order
				return result;
			}
			// otherwise, compare the tools
			int e1Position = -1, e2Position = -1;

			if (st1.actiontool != null) {
				if (st1.actiontool.hasPosition())
					e1Position = st1.actiontool.getPosition();
			} else if (st1.selectabletool != null) {
				if (st1.selectabletool.hasPosition())
					e1Position = st1.selectabletool.getPosition();
			}

			if (st2.actiontool != null) {
				if (st2.actiontool.hasPosition())
					e2Position = st2.actiontool.getPosition();
			} else if (st2.selectabletool != null) {
				if (st2.selectabletool.hasPosition())
					e2Position = st2.selectabletool.getPosition();
			}

			if (e1Position == -1 && e2Position != -1) {
				return 1;
			}
			if (e1Position != -1 && e2Position == -1) {
				return -1;
			}
			if (e1Position != -1 && e2Position != -1) {
				result = e1Position - e2Position;
				// we don't return 0 unless both objects are the same, otherwise
				// the objects get overwritten in the treemap
				if (result != 0)
					return result;
			}
			return st1.toString().compareTo(st2.toString());
		}
	}

	/**
	 * validates the user before starting gvsig
	 * 
	 */
	private static void validate() {

		IAuthentication session = null;
		try {
			session = (IAuthentication) Class.forName(
					"com.iver.andami.authentication.Session").newInstance();

		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		}

		session.setPluginDirectory(andamiConfig.getPluginsDirectory());
		if (session.validationRequired()) {
			if (session.Login()) {
				System.out.println("You are logged in");
			} else {
				JOptionPane.showMessageDialog(
						(Component) PluginServices.getMainFrame(),
						"You are not logged in");
				// System.exit(0);
			}
			PluginServices.setAuthentication(session);
		}
	}

	public static String getDefaultLookAndFeel() {
		String osName = System.getProperty("os.name");

		if (osName.length() > 4
				&& osName.substring(0, 5).toLowerCase().equals("linux"))
			return nonWinDefaultLookAndFeel;
		if (osName.toLowerCase().startsWith("mac os x"))
			return "ch.randelshofer.quaqua.QuaquaLookAndFeel";

		return UIManager.getSystemLookAndFeelClassName();
	}

	/**
	 * Gets the ISO 839 two-characters-long language code matching the provided
	 * language code (which may be an ISO 839-2/T three-characters-long code or
	 * an ISO 839-1 two-characters-long code).
	 * 
	 * If the provided parameter is already two characters long, it returns the
	 * parameter without any modification.
	 * 
	 * @param langCode
	 *            A language code representing either an ISO 839-2/T language
	 *            code or an ISO 839-1 code.
	 * @return A two-characters-long code specifying an ISO 839 language code.
	 */
	private static String normalizeLanguageCode(String langCode) {
		final String fileName = "iso_639.tab";
		if (langCode.length() == 2)
			return langCode;
		else if (langCode.length() == 3) {
			if (langCode.equals("va") || langCode.equals("val")) { // special
																	// case for
																	// Valencian
				return "ca";
			}
			URL isoCodes = Launcher.class.getClassLoader()
					.getResource(fileName);
			if (isoCodes != null) {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(isoCodes.openStream(),
									"ISO-8859-1"));
					String line;

					while ((line = reader.readLine()) != null) {
						String[] language = line.split("\t");
						if (language[0].equals(langCode)) // first column is the
															// three characters
															// code
							return language[2]; // third column i the two
												// characters code
					}
				} catch (IOException ex) {
					logger.error(
							Messages.getString("Error_reading_isocodes_file"),
							ex);
					return "es";
				}
			} else {
				logger.error(Messages.getString("Error_reading_isocodes_file"));
				return "es";
			}
		}
		return "es";
	}

	/**
	 * Configures the locales (languages and local resources) to be used by the
	 * application.
	 * 
	 * First it tries to get the locale from the command line parameters, then
	 * the andami-config file is checked.
	 * 
	 * The locale name is normalized to get a two characters language code as
	 * defined by ISO-639-1 (although ISO-639-2/T three characters codes are
	 * also accepted from the command line or the configuration file).
	 * 
	 * Finally, the gvsig-i18n library and the default locales for Java and
	 * Swing are configured.
	 * 
	 */
	private static void configureLocales(String[] args) {
		// Configurar el locale
		String localeStr = null;
		/*
		 * for (int i=2; i < args.length; i++) { int index =
		 * args[i].indexOf("language="); if (index != -1) localeStr =
		 * args[i].substring(index+9); }
		 */
		localeStr = PluginServices.getArgumentByName("language");
		if (localeStr == null) {
			localeStr = andamiConfig.getLocaleLanguage();
		}
		localeStr = normalizeLanguageCode(localeStr);
		locale = getLocale(localeStr, andamiConfig.getLocaleCountry(),
				andamiConfig.getLocaleVariant());
		Locale.setDefault(locale);
		JComponent.setDefaultLocale(locale);
		org.gvsig.i18n.Messages.addLocale(locale);
		// add english and spanish as fallback languages
		if (localeStr.equals("es") || localeStr.equals("ca")
				|| localeStr.equals("gl") || localeStr.equals("eu")
				|| localeStr.equals("va")) {
			// prefer Spanish for languages spoken in Spain
			org.gvsig.i18n.Messages.addLocale(new Locale("es"));
			org.gvsig.i18n.Messages.addLocale(new Locale("en"));
		} else {
			// prefer English for the rest
			org.gvsig.i18n.Messages.addLocale(new Locale("en"));
			org.gvsig.i18n.Messages.addLocale(new Locale("es"));
		}
		org.gvsig.i18n.Messages.addResourceFamily("com.iver.andami.text",
				"com.iver.andami.text");

	}

	/**
	 * Gets Home Directory location of the application. May be set from outside
	 * the aplication by means of -DgvSIG.home=C:/data/gvSIG, where gvSIG its
	 * the name of the application
	 * 
	 * @return
	 */
	public static String getAppHomeDir() {
		return appHomeDir;
	}

	/**
	 * Sets Home Directory location of the application. May be set from outside
	 * the aplication by means of -DgvSIG.home=C:/data/gvSIG, where gvSIG its
	 * the name of the application
	 * 
	 * @param appHomeDir
	 */
	public static void setAppHomeDir(String appHomeDir) {
		Launcher.appHomeDir = appHomeDir;
	}

	/**
	 * Initialize the extesion that have to take the control of the state of
	 * action controls of the UI of all extensions. <br>
	 * <br>
	 * For use this option you have to add an argument to the command line like
	 * this: <br>
	 * <br>
	 * -exclusiveUI={pathToExtensionClass} <br>
	 * 
	 * @see com.iver.andami.plugins.IExtension#isEnabled(IExtension extension)
	 * @see com.iver.andami.plugins.IExtension#isVisible(IExtension extension)
	 */
	private static void initializeExclusiveUIExtension() {
		String name = PluginServices.getArgumentByName("exclusiveUI");
		if (name == null)
			return;

		Iterator<Class<?>> iter = classesExtensions.keySet().iterator();
		int charIndex;
		Class<?> key;
		while (iter.hasNext()) {
			key = iter.next();
			charIndex = key.getName().indexOf(name);
			// System.out.println("key='"+key.getName()+"' name='"+name+"' charIndex="+charIndex);
			if (charIndex == 0) {
				IExtension ext = classesExtensions.get(key);
				if (ext instanceof ExtensionDecorator)
					ext = ((ExtensionDecorator) ext).getExtension();
				if (ext instanceof ExclusiveUIExtension)
					PluginServices
							.setExclusiveUIExtension((ExclusiveUIExtension) ext);
				break;
			}
		}

		logger.error(Messages
				.getString("No_se_encontro_la_extension_especificada_en_el_parametro_exclusiveUI")
				+ " '" + name + "'");
	}

	// public static void initIconThemes() {
	// // load the iconTheme
	// IconThemeManager iconManager = new IconThemeManager();
	// PluginServices.setIconThemeManager(iconManager);
	// IconThemeInfo selectedTheme = iconManager.readConfig();
	// if (selectedTheme!=null) {
	// iconManager.setDefault(selectedTheme);
	// logger.info("Setting the icon theme: "+selectedTheme.toVerboseString());
	// }
	// else {
	// // set the default dir and try to load the default theme
	// try {
	// iconManager.setThemesDir(new File("iconThemes"));
	// IconThemeInfo[] list = iconManager.list();
	//
	// for (int i=0; i<list.length; i++) {
	// if (list[i].getResourceName().equals("iconThemes/icons")) {
	// iconManager.setDefault(list[i]);
	// logger.info("Setting the default icon theme: "+list[i].toVerboseString());
	// return;
	// }
	// }
	// } catch (FileNotFoundException e) {
	// logger.info("IconTheme basedir does not exist");
	// }
	// // create an empty theme
	// IconThemeInfo info = new IconThemeInfo();
	// info.setName("No theme loaded");
	// info.setResource(null); // null resource means that no real theme is
	// loaded
	// info.setDescription("No theme loaded");
	// info.setVersion("0");
	// iconManager.setDefault(new IconTheme(info));
	// logger.info("Setting an empty icon theme");
	//
	// }
	// }

	public static void initIconThemes() {
		IconThemeManager iconManager = IconThemeManager.getIconThemeManager();
		IIconTheme icontheme = iconManager.getIconThemeFromConfig();
		if (icontheme != null) {
			iconManager.setCurrent(icontheme);
		}
	}

	/**
	 * Manages Andami termination process
	 * 
	 * @author Cesar Martinez Izquierdo <cesar.martinez@iver.es>
	 */
	public class TerminationProcess {
		private boolean proceed = false;
		private UnsavedDataPanel panel = null;

		public void run() {
			int exit = manageUnsavedData();
			if (exit == JOptionPane.NO_OPTION
					|| exit == JOptionPane.CLOSED_OPTION) {
				// the user doesn't want to exit
				return;
			}

			closeAndami();
		}

		/**
		 * Finishes the application without asking user if want or not to save
		 * unsaved data.
		 */
		public void closeAndami() {
			// Configuraci�n de Andami
			try {
				andamiConfigToXML(andamiConfigPath);
			} catch (MarshalException e) {
				logger.error(
						Messages.getString("Launcher.No_se_pudo_guardar_la_configuracion_de_andami"),
						e);
			} catch (ValidationException e) {
				logger.error(
						Messages.getString("Launcher.No_se_pudo_guardar_la_configuracion_de_andami"),
						e);
			} catch (IOException e) {
				logger.error(
						Messages.getString("Launcher.No_se_pudo_guardar_la_configuracion_de_andami"),
						e);
			}

			// Persistencia de los plugins
			savePluginPersistence();

			// Finalize all the extensions
			finalizeExtensions();

			// Clean any temp data created
			Utilities.cleanUpTempFiles();

			// Para la depuraci�n de memory leaks
			System.gc();

			System.exit(0);
		}

		/**
		 * Exectutes the terminate method for all the extensions, in the reverse
		 * order they were initialized
		 * 
		 */
		private void finalizeExtensions() {
			for (int i = extensions.size() - 1; i >= 0; i--) {
				extensions.get(i).terminate();
			}
		}

		private ArrayList<IUnsavedData> getUnsavedData() {
			ArrayList<IUnsavedData> unsavedDataList = new ArrayList<IUnsavedData>();
			IExtension exclusiveExtension = PluginServices
					.getExclusiveUIExtension();

			for (int i = extensions.size() - 1; i >= 0; i--) {
				IExtension extensionInstance = extensions.get(i);
				IExtensionStatus status = null;
				if (exclusiveExtension != null) {
					status = exclusiveExtension.getStatus(extensionInstance);
				} else {
					status = extensionInstance.getStatus();
				}
				if (status != null) {
					if (status.hasUnsavedData()) {
						IUnsavedData[] array = status.getUnsavedData();
						for (int element = 0; element < array.length; element++) {
							unsavedDataList.add(array[element]);
						}
					}
				}
			}
			return unsavedDataList;
		}

		public UnsavedDataPanel getUnsavedDataPanel() {
			if (panel == null) {
				panel = new UnsavedDataPanel(new IUnsavedData[0]);
			}
			return panel;
		}

		/**
		 * Checks if the extensions have some unsaved data, and shows a dialog
		 * to allow saving it. This dialog also allows to don't exit Andami.
		 * 
		 * @return true if the user confirmed he wishes to exit, false otherwise
		 */
		public int manageUnsavedData() {
			ArrayList<IUnsavedData> unsavedDataList = getUnsavedData();

			// there was no unsaved data
			if (unsavedDataList.size() == 0) {
				int option = JOptionPane.showConfirmDialog(frame,
						Messages.getString("MDIFrame.quiere_salir"),
						Messages.getString("MDIFrame.salir"),
						JOptionPane.YES_NO_OPTION);
				return option;
			}

			// it does not work if we directly cast the array
			IUnsavedData[] unsavedDataArray;
			unsavedDataArray = new IUnsavedData[unsavedDataList.size()];
			System.arraycopy(unsavedDataList.toArray(), 0, unsavedDataArray, 0,
					unsavedDataList.size());

			UnsavedDataPanel panel = getUnsavedDataPanel();
			panel.setUnsavedDataArray(unsavedDataArray);

			panel.addActionListener(panel.new UnsavedDataPanelListener() {
				public void cancel(UnsavedDataPanel panel) {
					proceed(false);
					PluginServices.getMDIManager().closeWindow(panel);

				}

				public void discard(UnsavedDataPanel panel) {
					proceed(true);
					PluginServices.getMDIManager().closeWindow(panel);

				}

				public void accept(UnsavedDataPanel panel) {
					IUnsavedData[] unsavedDataArray = panel
							.getSelectedsUnsavedData();
					boolean saved;
					for (int i = 0; i < unsavedDataArray.length; i++) {
						try {
							saved = unsavedDataArray[i].saveData();
						} catch (Exception ex) {
							PluginServices.getLogger().error(
									"Error saving"
											+ unsavedDataArray[i]
													.getResourceName(), ex);
							saved = false;
						}
						if (!saved) {
							JOptionPane.showMessageDialog(
									panel,
									PluginServices
											.getText(this,
													"The_following_resource_could_not_be_saved_")
											+ "\n"
											+ unsavedDataArray[i]
													.getResourceName()
											+ " -- "
											+ unsavedDataArray[i]
													.getDescription(),
									PluginServices.getText(this,
											"Resource_was_not_saved"),
									JOptionPane.ERROR_MESSAGE);

							ArrayList<IUnsavedData> unsavedDataList = getUnsavedData();
							// it does not work if we directly cast the array
							unsavedDataArray = new IUnsavedData[unsavedDataList
									.size()];
							System.arraycopy(unsavedDataList.toArray(), 0,
									unsavedDataArray, 0, unsavedDataList.size());
							panel.setUnsavedDataArray(unsavedDataArray);
							return;
						}
					}
					proceed(true);
					PluginServices.getMDIManager().closeWindow(panel);
				}
			});

			PluginServices.getMDIManager().addWindow(panel);
			if (proceed) {
				return JOptionPane.YES_OPTION;
			} else {
				return JOptionPane.NO_OPTION;
			}
		}

		private void proceed(boolean proceed) {
			this.proceed = proceed;
		}

	}

	public static TerminationProcess getTerminationProcess() {
		return (new Launcher()).new TerminationProcess();
	}
}
