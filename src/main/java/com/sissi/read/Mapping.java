package com.sissi.read;

/**
 * @author kim 2013-10-25
 */
public interface Mapping {

	public Object newInstance(String uri, String localName);

	public Boolean hasCached(String uri, String localName);
}
