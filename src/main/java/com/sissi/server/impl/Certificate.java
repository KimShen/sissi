package com.sissi.server.impl;

import java.net.URL;

/**
 * @author kim 2013年12月23日
 */
public class Certificate {

	private URL file;

	private String password;

	public Certificate(URL file, String password) {
		super();
		this.file = file;
		this.password = password;
	}

	public URL getFile() {
		return this.file;
	}

	public char[] getPassword() {
		return password.toCharArray();
	}
}