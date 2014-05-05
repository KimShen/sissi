package com.sissi.resource;

/**
 * 资源加载/释放计数器
 * 
 * @author kim 2014年1月15日
 */
public interface ResourceCounter {

	public ResourceCounter increment(String resource);

	public ResourceCounter decrement(String resource);
}
