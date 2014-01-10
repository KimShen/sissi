package com.sissi.context;

/**
 * Online status of jid with special resource
 * 
 * @author kim 2013年12月23日
 */
public interface Status {

	/**
	 * Update
	 * 
	 * @param type
	 * @param show
	 * @param status
	 * @param avator
	 * @return
	 */
	public Status setStatus(String type, String show, String status, String avator);

	public StatusClauses getStatusClauses();

	public Status clear();
}