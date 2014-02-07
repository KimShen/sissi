package com.sissi.context;

/**
 * @author kim 2014年2月3日
 */
public interface JIDs extends Iterable<JID> {

	public boolean isEmpty();

	public boolean moreThan(Integer counter);
	
	public boolean lessThan(Integer counter);
}
