package com.iver.andami.authentication;

import java.util.Map;

/**
 * Interface to implement by clases which perform the authentication
 * 
 * @author laura
 */
public interface IAuthentication extends Map<String, String> {

	public boolean Login();

	public boolean isLogged();

	public void setLogged(boolean logged);

	public boolean isValidUser();

	public boolean validationRequired();

	public void setPluginDirectory(String dir);

}
