package com.sissi.ucenter.block;

import com.sissi.context.JID;
import com.sissi.context.JIDs;

/**
 * 黑名单策略
 * 
 * @author kim 2013年12月6日
 */
public interface BlockContext {

	/**
	 * 加入黑名单
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public BlockContext block(JID from, JID to);

	/**
	 * 移除全部黑名单
	 * 
	 * @param from
	 * @return
	 */
	public BlockContext unblock(JID from);

	/**
	 * 移除指定黑名单
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public BlockContext unblock(JID from, JID to);

	/**
	 * 我禁止的JID
	 * 
	 * @param jid
	 * @return
	 */
	public JIDs iBlockWho(JID jid);

	/**
	 * To是否被From禁止
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean isBlock(JID from, JID to);
}
