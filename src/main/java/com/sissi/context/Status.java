package com.sissi.context;

/**
 * @author kim 2014年1月15日
 */
public interface Status {
	
	public Status setClauses(StatusClauses clauses) ;

	public StatusClauses getClauses();

	public Status clear();
}