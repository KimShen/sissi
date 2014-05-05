package com.sissi.context;

/**
 * 出席状态
 * 
 * @author kim 2014年1月15日
 */
public interface Status {

	/**
	 * 清除出席状态
	 * 
	 * @return
	 */
	public Status clear();

	/**
	 * 保存出席状态(Push)
	 * 
	 * @param clauses
	 * @return
	 */
	public Status clauses(StatusClauses clauses);

	/**
	 * 同步出席状态(Pull)
	 * 
	 * @return
	 */
	public StatusClauses clauses();
}