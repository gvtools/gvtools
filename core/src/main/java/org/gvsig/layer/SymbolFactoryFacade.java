package org.gvsig.layer;

import java.awt.Color;

import org.geotools.styling.Style;

/**
 * This interface contains some convenience methods to build styles in a simple
 * way
 * 
 * @author Fernando González Cortés
 */
public interface SymbolFactoryFacade {

	Style newLineStyle(Color color, int width);

}
