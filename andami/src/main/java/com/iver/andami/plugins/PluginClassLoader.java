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
package com.iver.andami.plugins;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.iver.andami.messages.Messages;

/**
 * <p>
 * Class loader which loads the classes requested by the plugins. It first tries
 * to search in the classpath, then it requests the class to the parent
 * classloader, then it searches in the owns plugins' library dir, and if all
 * these methods fail, it tries to load the class from any of the depended
 * plugins. Finally, if this also fails, the other classloaders provided in the
 * <code>addLoaders</code> method are requested to load the class.
 * </p>
 * 
 * <p>
 * The class loader can also be used to load resources from the plugin's
 * directory by using the <code>getResource()</code> method.
 * </p>
 * 
 * @author Fernando González Cortés
 */
public class PluginClassLoader extends URLClassLoader {
	/** DOCUMENT ME! */
	private static Logger logger = Logger.getLogger(PluginClassLoader.class
			.getName());

	/** DOCUMENT ME! */
	private Hashtable<String, ZipFile> clasesJar = new Hashtable<String, ZipFile>();

	/** DOCUMENT ME! */
	private File baseDir;
	private PluginClassLoader[] pluginLoaders;
	private static ArrayList<ClassLoader> otherLoaders = new ArrayList<ClassLoader>();
	private boolean isOtherLoader = false;

	/**
	 * Creates a new PluginClassLoader object.
	 * 
	 * @param jars
	 *            Array with the search paths where classes will be searched
	 * @param baseDir
	 *            Base directory for this plugin. This is the directory which
	 *            will be used as basedir in the <code>getResources</code>
	 *            method.
	 * @param cl
	 *            The parent classloader of this classloader. It will be used to
	 *            search classes before trying to search in the plugin's
	 *            directory
	 * @param pluginLoaders
	 *            The classloaders of the depended plugins.
	 * 
	 * @throws IOException
	 */
	public PluginClassLoader(URL[] jars, String baseDir, ClassLoader cl,
			PluginClassLoader[] pluginLoaders) throws IOException {
		super(jars, cl);
		this.baseDir = new File(new File(baseDir).getAbsolutePath());
		this.pluginLoaders = pluginLoaders;

		ZipFile[] jarFiles = new ZipFile[jars.length];

		for (int i = 0; i < jars.length; i++) {
			try {
				jarFiles[i] = new ZipFile(jars[i].getPath());

				Enumeration<? extends ZipEntry> entradas = jarFiles[i]
						.entries();

				while (entradas.hasMoreElements()) {
					ZipEntry file = entradas.nextElement();
					String fileName = file.getName();

					if (!fileName.toLowerCase().endsWith(".class")) { //$NON-NLS-1$

						continue;
					}

					fileName = fileName.substring(0, fileName.length() - 6)
							.replace('/', '.');

					if (clasesJar.get(fileName) != null) {
						throw new JarException(
								Messages.getString("PluginClassLoader.Dos_clases_con_el_mismo_nombre_en_el_plugin")
										+ ": "
										+ fileName
										+ " "
										+ Messages.getString("en")
										+ " "
										+ jarFiles[i].getName()
										+ Messages.getString("y_en")
										+ " "
										+ clasesJar.get(fileName).getName());
					}

					clasesJar.put(fileName, jarFiles[i]);
				}
			} catch (ZipException e) {
				throw new IOException(e.getMessage() + " Jar: "
						+ jars[i].getPath() + ": " + jarFiles[i]);
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 * 
	 * @throws ClassNotFoundException
	 *             DOCUMENT ME!
	 */
	protected Class<?> singleLoadClass(String name)
			throws ClassNotFoundException {
		// Buscamos en las clases de las librerías del plugin
		Class<?> c = findLoadedClass(name);

		if (c != null) {
			return c;
		}

		try {
			ZipFile jar = clasesJar.get(name);

			// No está en ningún jar
			if (jar == null) {
				// Buscamos en el directorio de clases
				String classFileName = baseDir + "/classes/"
						+ name.replace('.', '/') + ".class";
				File f = new File(classFileName);
				if (f.exists()) {
					byte[] data = loadClassData(f);
					c = defineClass(name, data, 0, data.length);
				} else {
					// Buscamos en los otros plugins
					for (int i = 0; i < pluginLoaders.length; i++) {
						try {
							if (pluginLoaders[i] != null) {
								c = pluginLoaders[i].singleLoadClass(name);
							} else {
								// TODO El pluginLoaders[i] puede ser nulo?
								logger.warn("PluginLoaders[i] es nulo");
							}
						} catch (ClassNotFoundException e) {
							// Si no la encontramos en el primer plugin,
							// capturamos la exceptción
							// porque es probable que la encontremos en el resto
							// de plugins.
						}

						if (c != null) {
							break;
						}
					}
				}
			} else {
				String fileName = name.replace('.', '/') + ".class";
				ZipEntry classFile = jar.getEntry(fileName);
				byte[] data = loadClassData(classFile,
						jar.getInputStream(classFile));

				c = defineClass(name, data, 0, data.length);
			}

			if (c == null) {
				throw new ClassNotFoundException(name);
			}

			return c;
		} catch (IOException e) {
			throw new ClassNotFoundException(
					Messages.getString("PluginClassLoader.Error_reading_file")
							+ name);
		}
	}

	/**
	 * Carga la clase
	 * 
	 * @param name
	 *            Nombre de la clase
	 * @param resolve
	 *            Si se ha de resolver la clase o no
	 * 
	 * @return Clase cargada
	 * 
	 * @throws ClassNotFoundException
	 *             Si no se pudo encontrar la clase
	 */
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> c = null;

		// Intentamos cargar con el system classloader
		try {
			if (!isOtherLoader)
				c = super.loadClass(name, resolve);
		} catch (ClassNotFoundException e1) {
			try {
				c = singleLoadClass(name);
			} catch (ClassNotFoundException e2) {
				try {
					isOtherLoader = true;
					c = loadOtherClass(name);
				} catch (ClassNotFoundException e3) {
					throw new ClassNotFoundException(
							Messages.getString("PluginClassLoader.Error_reading_file")
									+ name);
				} finally {
					isOtherLoader = false;
				}
			}
		}
		if (c == null)
			throw new ClassNotFoundException(
					Messages.getString("PluginClassLoader.Error_reading_file")
							+ name);
		if (resolve) {
			resolveClass(c);
		}
		return c;
	}

	private Class<?> loadOtherClass(String name) throws ClassNotFoundException {
		ClassLoader[] ocl = otherLoaders.toArray(new ClassLoader[0]);
		for (int i = 0; i < ocl.length; i++) {
			Class<?> c = ocl[i].loadClass(name);
			if (c != null)
				return c;
		}
		throw new ClassNotFoundException();
	}

	/**
	 * obtiene el array de bytes de la clase
	 * 
	 * @param classFile
	 *            Entrada dentro del jar contiene los bytecodes de la clase (el
	 *            .class)
	 * @param is
	 *            InputStream para leer la entrada del jar
	 * 
	 * @return Bytes de la clase
	 * 
	 * @throws IOException
	 *             Si no se puede obtener el .class del jar
	 */
	private byte[] loadClassData(ZipEntry classFile, InputStream is)
			throws IOException {
		// Get size of class file
		int size = (int) classFile.getSize();

		// Reserve space to read
		byte[] buff = new byte[size];

		// Get stream to read from
		DataInputStream dis = new DataInputStream(is);

		// Read in data
		dis.readFully(buff);

		// close stream
		dis.close();

		// return data
		return buff;
	}

	/**
	 * Gets the bytes of a File
	 * 
	 * @param file
	 *            File
	 * 
	 * @return bytes of file
	 * 
	 * @throws IOException
	 *             If the operation fails
	 */
	private byte[] loadClassData(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;

		while ((offset < bytes.length)
				&& ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();

		return bytes;
	}

	/**
	 * Gets the requested resource. If the path is relative, its base directory
	 * will be the one provided in the PluginClassLoader's constructor. If the
	 * resource is not found, the parent classloader will be invoked to try to
	 * get it. If it is not found, it will return null.
	 * 
	 * @param res
	 *            An absolute or relative path to the requested resource.
	 * 
	 * @return Resource's URL if it was found, nul otherwise.
	 */
	public URL getResource(String res) {
		try {
			ArrayList<String> resource = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(res, "\\/");

			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				resource.add(token);
			}

			URL ret = getResource(baseDir, resource);

			if (ret != null) {
				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getResource(res);
	}

	/**
	 * Gets the requested resource. If the path is relative, its base directory
	 * will be the one provided in the PluginClassLoader's constructor. If the
	 * resource is not found, the parent classloader will be invoked to try to
	 * get it. If it is not found, it will return null.
	 * 
	 * @param res
	 *            An absolute or relative path to the requested resource.
	 * 
	 * @return Resource's URL if it was found, nul otherwise.
	 */
	private URL getResource(File base, List<String> res) {
		File[] files = base.listFiles();

		String parte = res.get(0);

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().compareTo(parte) == 0) {
				if (res.size() == 1) {
					try {
						return new URL("file:" + files[i].toString());
					} catch (MalformedURLException e) {
						return null;
					}
				} else {
					return getResource(files[i], res.subList(1, res.size()));
				}
			}
		}

		return null;
	}

	/**
	 * Returns the name of the plugin (the name of the directory containing the
	 * plugin).
	 * 
	 * @return An String containing the plugin's name.
	 */
	public String getPluginName() {
		String ret = baseDir.getAbsolutePath().substring(
				baseDir.getAbsolutePath().lastIndexOf(File.separatorChar) + 1);

		return ret;
	}

	/*
	 * @see
	 * java.security.SecureClassLoader#getPermissions(java.security.CodeSource)
	 */
	protected PermissionCollection getPermissions(CodeSource codesource) {
		PermissionCollection perms = super.getPermissions(codesource);
		perms.add(new AllPermission());

		return perms;
	}

	/**
	 * Gets the plugin's base dir, the directory which will be used to search
	 * resources.
	 * 
	 * @return Returns the baseDir.
	 */
	public String getBaseDir() {
		return baseDir.getAbsolutePath();
	}

	/**
	 * Adds other classloader to use when all the normal methods fail.
	 * 
	 * @param classLoaders
	 *            An ArrayList of ClassLoaders which will be used to load
	 *            classes when all the normal methods fail.
	 */
	public static void addLoaders(ArrayList<ClassLoader> classLoaders) {
		otherLoaders.addAll(classLoaders);
	}
}
