package com.iver.andami.ui.mdiManager;

/**
 * Interface to transform a standard view to a view palette.
 * 
 * @author Vicente Caballero Navarro
 */
public interface IWindowTransform {
	/**
	 * Transform to view palette.
	 */
	public void toPalette();

	/**
	 * Restore to standard view
	 */
	public void restore();

	/**
	 * Returns if the view is palette type or standard
	 * 
	 * @return boolean
	 */
	public boolean isPalette();
}
