package com.sissi.resource;

/**
 * @author kim 2014年1月15日
 */
public interface ResourceMonitor {

	public ResourceMonitor increment();

	public ResourceMonitor decrement();
}
