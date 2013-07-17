package com.iver.cit.gvsig.project.documents.view.legend.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.gvsig.layer.Layer;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.Messages;

public abstract class AbstractThemeManagerPage extends JPanel {
	/**
	 * Cuando hay varios capas vectoriales seleccionados, devolver� el �ltimo.
	 * 
	 * @param layers
	 *            Grupo de layers.
	 * 
	 * @return la primera flayer seleccionada.
	 */
	protected FLayer getFirstActiveLayerVect(FLayers layers) {
		// Comprobar en openLegendManager que hay alg�n capa activo!
		FLayer[] activeLyrs = layers.getActives();

		if (activeLyrs.length == 0) {
			JOptionPane.showMessageDialog(null,
					Messages.getString("necesita_una_capa_activa"), "",
					JOptionPane.ERROR_MESSAGE);

			return null;
		}

		FLayer lyr = null;

		for (int i = 0; i < activeLyrs.length; i++) {
			if (activeLyrs[i] instanceof FLayers) {
				lyr = getFirstActiveLayerVect((FLayers) activeLyrs[i]);
			}

			if (activeLyrs[i] instanceof Classifiable) {
				Classifiable auxC = (Classifiable) activeLyrs[i];
				ILegend theLegend = auxC.getLegend();

				if (theLegend instanceof IVectorLegend) {
					lyr = (FLayer) auxC;
				}
			}
		}

		if (lyr == null) {
			JOptionPane.showMessageDialog(null, Messages
					.getString(PluginServices.getText(this,
							"necesita_una_capa_vectorial_activa")
							+ "\n\n"
							+ PluginServices.getText(this,
									"Por_favor_active_la_capa") + "."), "",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return lyr;
	}

	/**
	 * Returns the name of this ThemeManagerPage's tab, the text returned by
	 * this method will be shown in the text of this panel's tab.
	 */
	public abstract String getName();

	/**
	 * <p>
	 * Method invoked when the Ok button is pressed from the ThemeManagerWindow.
	 * It will cause the changes performed by the user to take effect into the
	 * layer if the Apply button wasn't pressed yet. In case Apply button was
	 * pressed, then the programmer can choose between apply the changes again
	 * or not.<br>
	 * </p>
	 * <p>
	 * It shouldn't be a problem rather than the potential consumption of time
	 * required in when applying such changes.<br>
	 * </p>
	 * <p>
	 * <b>Notice</b> that after the call of this method the ThemeManagerWindow
	 * will be closed.
	 * </p>
	 */
	public abstract void acceptAction();

	/**
	 * <p>
	 * Method invoked when the Cancel button is pressed from the
	 * ThemeManagerWindow. It will cause that the changes performed will be
	 * discarded.
	 * </p>
	 */
	public abstract void cancelAction();

	/**
	 * Method invoked when the Apply button is pressed from the
	 * ThemeManagerWindow. It will cause the changes performed by the user to
	 * take effect inmediately into the the layer.
	 */
	public abstract void applyAction();

	/**
	 * This method is invoked during the initialization of the
	 * ThemeManagerWindow and causes the dialog to be updated to reflect the
	 * current settings of the layer in the context that this panel was designed
	 * for.
	 * 
	 * @param layer
	 *            , the target FLayer
	 */
	public abstract void setModel(Layer layer);

	/**
	 * Returns true if the tab should be shown for the specified layer. Returs
	 * false otherwise.
	 * 
	 * @param layer
	 * @return
	 */
	public abstract boolean accepts(Layer layer);
}
