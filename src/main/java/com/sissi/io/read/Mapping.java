package com.sissi.io.read;

/**
 * @author kim 2013-10-25
 */
public interface Mapping {

	public Object instance(String uri, String localName);

	public boolean cached(String uri, String localName);
}
