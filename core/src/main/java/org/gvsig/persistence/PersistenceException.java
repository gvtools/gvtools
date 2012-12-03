package org.gvsig.persistence;

/**
 * Indicates a problem in a persistence operation, typically an instance could
 * not be built from the XML objects
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenceException(String message, Exception cause) {
		super(message, cause);
	}
}
