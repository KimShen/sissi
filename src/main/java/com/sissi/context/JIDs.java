package com.sissi.context;

/**
 * @author kim 2014年2月3日
 */
public interface JIDs extends Iterable<JID> {

	public JID jid();

	public boolean isEmpty();

	/**
	 * Full JID的比较
	 * 
	 * @param jid
	 * @return
	 */
	public boolean same(JID jid);

	/**
	 * Bare JID的比较
	 * 
	 * @param jid
	 * @return
	 */
	public boolean like(JID jid);

	/**
	 * 是否小于指定数量
	 * 
	 * @param counter
	 * @return
	 */
	public boolean lessThan(Integer counter);
}
