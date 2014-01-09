package com.sissi.context;

/**
 * @author kim 2013年12月23日
 */
public interface Status {

	public Status setStatus(String type, String show, String status, String avator);
	
	public Status clear();
	
	public StatusClauses getStatus();
}