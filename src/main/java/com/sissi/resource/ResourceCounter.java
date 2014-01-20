package com.sissi.resource;

/**
 * @author kim 2014年1月15日
 */
public interface ResourceCounter {

	public ResourceCounter increment();

	public ResourceCounter decrement();
}
