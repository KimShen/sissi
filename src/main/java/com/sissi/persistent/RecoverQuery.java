package com.sissi.persistent;

/**
 * @author kim 2014年3月6日
 */
public interface RecoverQuery {

	/**
	 * 数据流长度
	 * 
	 * @param limit
	 * @param def
	 * @return
	 */
	public int limit(int limit, int def);

	/**
	 * 数据流Timeline
	 * 
	 * @param limit
	 * @param def
	 * @return
	 */
	public long since(long limit, long def);

	/**
	 * 数据流方向
	 * 
	 * @param direction
	 * @return
	 */
	public boolean direction(RecoverDirection direction);
}
