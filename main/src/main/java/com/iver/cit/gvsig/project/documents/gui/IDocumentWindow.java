/**
 * 
 */
package com.iver.cit.gvsig.project.documents.gui;

/**
 * @author Miguel Ángel Querol Carratalá <querol_mig@gva.es>
 * 
 *         Interfaz que tienen que implementar las clases de interfaz de usuario
 *         de los documentos que se quieran insertar en gvSIG. Estas clases se
 *         usarán para cargar y guardar las propiedades graficas de la ventana
 *         del documento como tamaños y posiciones.
 */
public interface IDocumentWindow {

	/**
	 * Método para obtener un windowData con las propiedades de la ventana del
	 * documento como pueden ser tamaños, posiciones y estados de sliders,
	 * divisores etc. Este método será llamado para guardar los datos a disco.
	 * Lo llamará la clase project para obtener los datos y asi guardarlos.
	 * 
	 * @return un windowData con los datos de la ventana.
	 */
	public WindowData getWindowData();

	/**
	 * Método para cargar los datos de la ventana de proyecto. El widowData
	 * guardado se le pasa a la clase de interfaz de usuario correspondiente a
	 * la ventana del documento.
	 * 
	 * @param winData
	 */
	public void setWindowData(WindowData winData);

}
