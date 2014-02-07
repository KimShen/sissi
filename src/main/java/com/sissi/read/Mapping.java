package com.sissi.read;


/**
 * @author kim 2013-10-25
 */
public interface Mapping {

	public Object instance(String uri, String localName);

	public boolean exists(String uri, String localName);
}
