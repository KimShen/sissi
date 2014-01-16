package com.sissi.context;

/**
 * @author kim 2014年1月15日
 */
public interface Status {
	
	public Status setStatus(StatusClauses clauses) ;

	public StatusClauses getStatusClauses();

	public Status clear();
}