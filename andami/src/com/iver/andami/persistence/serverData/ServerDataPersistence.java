package com.iver.andami.persistence.serverData;

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
/* CVS MESSAGES:
 *
 * $Id: ServerDataPersistence.java 6976 2006-09-01 13:40:10Z jorpiell $
 * $Log$
 * Revision 1.7  2006-09-01 13:39:38  jorpiell
 * Se ha añadido la capacidad de almacenar properties en los server data
 *
 * Revision 1.6  2006/09/01 02:23:09  luisw2
 * Geters and Seters added
 *
 * Revision 1.5  2006/08/28 07:56:54  jaume
 * *** empty log message ***
 *
 * Revision 1.4  2006/05/19 06:41:50  jaume
 * removed unnecessary imports
 *
 * Revision 1.3  2006/05/02 15:53:06  jorpiell
 * Se ha cambiado la interfaz Extension por dos clases: una interfaz (IExtension) y una clase abstract(Extension). A partir de ahora todas las extensiones deben heredar de Extension
 *
 * Revision 1.2  2006/03/21 18:06:46  jorpiell
 * Se ha hecho una modificación para continuar soportando los servidores anteriores
 *
 * Revision 1.1  2006/03/07 11:32:13  jorpiell
 * Se ha añadido la clase ServerDataPersistence en el paquete persistence.serverData que se usa para persistir los servidores por orden de último acceso.
 *
 *
 */

import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.iver.andami.PluginServices;
import com.iver.utiles.DateTime;
import com.iver.utiles.NotExistInXMLEntity;
import com.iver.utiles.XMLEntity;
import com.iver.utiles.swing.jcomboServer.ServerData;

/**
 * This class is used to save a list of servers (using the Andami persistence
 * model) to the plugins-persistence.xml file. It has methods to create a set of
 * ServerData objects from an xml file. It can also save a set of ServerData
 * objects in an xml file.
 * 
 * @see es.gva.cit.catalogClient.utils.comboserver.ServerData
 * @author Jorge Piera Llodra (piera_jor@gva.es)
 */
public class ServerDataPersistence {
	private XMLEntity xml = null;
	private PluginServices ps = null;
	private String serviceType = null;
	private static final String SERVER_URL = "serverURL";
	private static final String SERVER_ADDED = "added";
	private static final String SERVER_LASTACCESS = "lastAccess";
	private static final String SERVER_TYPE = "type";
	private static final String SERVER_SUBTYPE = "subType";
	private static final String SERVER_DATABASE = "database";

	/**
	 * Constructor
	 * 
	 * @param view
	 *            The View Class
	 * @param sevice
	 *            Type Service type to load
	 */
	public ServerDataPersistence(Object pluginClassInstance, String serviceType) {
		ps = PluginServices.getPluginServices(pluginClassInstance);
		xml = ps.getPersistentXML();
		this.serviceType = serviceType;
	}

	/**
	 * This methos is used to save the information in an XML file
	 */
	public void setPersistent() {
		ps.setPersistentXML(xml);
	}

	/**
	 * This method saves an array of ServerData using the Anadami persistence
	 * model
	 * 
	 * @param servers
	 *            Array of servers
	 */
	public void setArrayOfServerData(ServerData[] servers) {
		xml.getXmlTag().removeAllXmlTag();

		for (int i = 0; i < servers.length; i++) {
			xml.addChild(serverDataToXml(servers[i]));
		}

	}

	/**
	 * This method adds a ServerData using the Anadami persistence model. If the
	 * server exists just actualizes the type and subtype fileds and changes the
	 * last access value to the current time.
	 * 
	 * @param server
	 *            ServerData
	 */
	public void addServerData(ServerData server) {
		ServerData[] servers = getArrayOfServerData();
		ServerData[] newServers = new ServerData[servers.length + 1];

		boolean found = false;

		for (int i = 0; i < servers.length; i++) {
			if (servers[i].getServerAddress().equals(server.getServerAddress())) {
				found = true;
				servers[i].updateLastAccess();
				servers[i].setServiceSubType(server.getServiceSubType());
				servers[i].setServiceType(server.getServiceType());
				servers[i].setDatabase(server.getDatabase());
				servers[i].setProperies(server.getProperies());
				setArrayOfServerData(servers);
			}
		}

		if (!found) {
			System.arraycopy(servers, 0, newServers, 0, servers.length);
			newServers[servers.length] = server;
			setArrayOfServerData(newServers);

		}
	}

	/**
	 * This method returns an array of ServerData objects that have been saved
	 * using the Andami persistence model.
	 * 
	 * @return String[]
	 */
	public ServerData[] getArrayOfServerData() {
		ServerData[] servers = new ServerData[xml.getChildrenCount()];
		for (int i = 0; i < xml.getChildrenCount(); i++) {
			servers[i] = xmlToServerData(xml.getChild(i));
		}
		return servers;
	}

	/**
	 * This method creates and returns a new XMLEntity.
	 * 
	 * @param server
	 *            ServerData with all the server information
	 * @return XMLEntity
	 */
	public XMLEntity serverDataToXml(ServerData server) {
		String dFormat = "Y-m-d H:i:s.Z";

		XMLEntity xmlEnt = new XMLEntity();
		xmlEnt.putProperty(SERVER_URL, server.getServerAddress());
		xmlEnt.putProperty(SERVER_ADDED,
				DateTime.dateToString(server.getAdded(), dFormat));
		xmlEnt.putProperty(SERVER_LASTACCESS,
				DateTime.dateToString(server.getLastAccess(), dFormat));
		xmlEnt.putProperty(SERVER_TYPE, server.getServiceType());
		xmlEnt.putProperty(SERVER_SUBTYPE, server.getServiceSubType());
		if (server.getServiceSubType().equals(
				ServerData.SERVER_SUBTYPE_CATALOG_Z3950)) {
			xmlEnt.putProperty(SERVER_DATABASE, server.getDatabase());
		}
		Set<Object> keys = server.getProperies().keySet();
		Iterator<Object> it = keys.iterator();
		while (it.hasNext()) {
			String next = (String) it.next();
			xmlEnt.putProperty(next, server.getProperies().get(next));
		}
		return xmlEnt;
	}

	/**
	 * This method creates a new serverData from a XMLEntity
	 * 
	 * @param xmlEnt
	 *            XMLRntity that contains the server information
	 * @return ServerData
	 */
	public ServerData xmlToServerData(XMLEntity xmlEnt) {
		String serverAddress;
		Date added;
		Date lastAccess;
		String serviceType = "";
		String serviceSubType = "";
		String databaseName = "";

		serverAddress = xmlEnt.getStringProperty(SERVER_URL);
		added = DateTime.stringToDate(xmlEnt.getStringProperty(SERVER_ADDED));
		lastAccess = DateTime.stringToDate(xmlEnt
				.getStringProperty(SERVER_LASTACCESS));
		serviceType = xmlEnt.getStringProperty(SERVER_TYPE).toUpperCase();
		serviceSubType = xmlEnt.getStringProperty(SERVER_SUBTYPE).toUpperCase();

		if (serviceSubType.equals(ServerData.SERVER_SUBTYPE_CATALOG_Z3950)) {
			try {
				databaseName = xmlEnt.getStringProperty(SERVER_DATABASE);
			} catch (NotExistInXMLEntity e) {

			}
		}

		ServerData serverData = new ServerData(serverAddress, added,
				lastAccess, serviceType, serviceSubType, databaseName);

		Properties props = new Properties();
		for (int i = 0; i < xmlEnt.getPropertyCount(); i++) {
			String property = xmlEnt.getPropertyName(i);
			if (!((property.equals(SERVER_URL))
					|| (property.equals(SERVER_ADDED))
					|| (property.equals(SERVER_LASTACCESS))
					|| (property.equals(SERVER_TYPE))
					|| (property.equals(SERVER_SUBTYPE)) || (property
						.equals(SERVER_DATABASE)))) {
				props.put(property, xmlEnt.getStringProperty(property));
			}
		}
		serverData.setProperies(props);
		return serverData;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public XMLEntity getXml() {
		return xml;
	}

	public void setXml(XMLEntity xml) {
		this.xml = xml;
	}
}
