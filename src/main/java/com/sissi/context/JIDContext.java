package com.sissi.context;

import java.net.SocketAddress;
import java.util.Collection;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	/**
	 * 全局唯一标记(Identity)
	 * 
	 * @return
	 */
	public long index();

	/**
	 * 绑定JID
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext jid(JID jid);

	public JID jid();

	/**
	 * 是否已身份验证
	 * 
	 * @return
	 */
	public boolean auth();

	/**
	 * 是否允许再次身份验证(Retry)
	 * 
	 * @return
	 */
	public boolean authRetry();

	/**
	 * 是否通过身份验证
	 * 
	 * @param canAccess
	 * @return
	 */
	public JIDContext auth(boolean canAccess);

	/**
	 * XMPP Binding
	 * 
	 * @return
	 */
	public JIDContext bind();

	public boolean binding();

	/**
	 * 优先级
	 * 
	 * @param priority
	 * @return
	 */
	public JIDContext priority(int priority);

	public int priority();

	/**
	 * XMPP域
	 * 
	 * @param domain
	 * @return
	 */
	public JIDContext domain(String domain);

	public String domain();

	public JIDContext lang(String lang);

	/**
	 * 默认语言
	 * 
	 * @return
	 */
	public String lang();

	/**
	 * 启动StartTLS
	 * 
	 * @return 是否启动成功
	 */
	public boolean encrypt();

	/**
	 * 是否已使用StartTLS
	 * 
	 * @return
	 */
	public boolean encrypted();

	/**
	 * 上线
	 * 
	 * @return
	 */
	public JIDContext online();

	public boolean onlined();

	/**
	 * 下线
	 * 
	 * @return
	 */
	public JIDContext offline();

	/**
	 * 最后活跃时间
	 * 
	 * @return
	 */
	public long idle();

	/**
	 * 出席状态
	 * 
	 * @return
	 */
	public Status status();

	/**
	 * 重置
	 * 
	 * @return
	 */
	public JIDContext reset();

	public SocketAddress address();

	/**
	 * 关闭
	 * 
	 * @return
	 */
	public boolean close();

	/**
	 * 预关闭
	 * 
	 * @return
	 */
	public boolean closePrepare();

	/**
	 * Ping/Pong
	 * 
	 * @return
	 */
	public boolean closeTimeout();

	/**
	 * 触发Ping
	 * 
	 * @return
	 */
	public JIDContext ping();

	/**
	 * 指定Ping
	 * 
	 * @param ping
	 * @return
	 */
	public JIDContext ping(int ping);

	public JIDContext pong(Element element);

	public JIDContext write(Element element);

	public JIDContext write(Element element, boolean force);

	/**
	 * @param element
	 * @param force 是否强制使用在线通道
	 * @param bare 是否使用裸JID(Bare JID)
	 * @return
	 */
	public JIDContext write(Element element, boolean force, boolean bare);

	public JIDContext write(Collection<Element> elements);

	public JIDContext write(Collection<Element> elements, boolean force);

	public JIDContext write(Collection<Element> elements, boolean force, boolean bare);
}
