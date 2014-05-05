package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;

/**
 * 寻址
 * 
 * @author kim 2014年1月15日
 */
public interface Addressing {

	/**
	 * 加入
	 * 
	 * @param context
	 * @return
	 */
	public Addressing join(JIDContext context);

	/**
	 * 移除
	 * 
	 * @param context
	 * @return
	 */
	public Addressing leave(JIDContext context);

	/**
	 * 更新指定上下文在寻址中的优先级
	 * 
	 * @param context
	 * @return
	 */
	public Addressing priority(JIDContext context);

	/**
	 * 获取上下文(集合)
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext find(JID jid);

	/**
	 * 获取上下文(集合)并指定是否过滤资源
	 * 
	 * @param jid
	 * @param usingResource
	 * @return
	 */
	public JIDContext find(JID jid, boolean usingResource);

	/**
	 * 获取唯一上下文,排序规则由实现类决定
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext findOne(JID jid);

	/**
	 * 获取唯一上下文并指定是否过滤资源
	 * 
	 * @param jid
	 * @param usingResource
	 * @return
	 */
	public JIDContext findOne(JID jid, boolean usingResource);

	/**
	 * 获取唯一上下文并指定是否使用离线上下文
	 * 
	 * @param jid
	 * @param usingResource
	 * @param offline
	 * @return
	 */
	public JIDContext findOne(JID jid, boolean usingResource, boolean offline);

	/**
	 * 获取JID所有在线资源
	 * 
	 * @param jid
	 * @return
	 */
	public JIDs resources(JID jid);

	/**
	 * 获取JID所有在线资源并指定是否过滤资源(指定资源是否已在线)
	 * 
	 * @param jid
	 * @param usingResource
	 * @return
	 */
	public JIDs resources(JID jid, boolean usingResource);
}
