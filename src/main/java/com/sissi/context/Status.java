package com.sissi.context;

/**
 * @author kim 2014年1月15日
 */
public interface Status {

	public Status clear();

	public Status clauses(StatusClauses clauses);

	public StatusClauses clauses();
}