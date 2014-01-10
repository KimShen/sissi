package com.sissi.context;

import java.net.SocketAddress;
import java.util.Collection;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	/**
	 * Internal index
	 * 
	 * @return
	 */
	public Long getIndex();

	public JIDContext setJid(JID jid);

	public JID getJid();

	public JIDContext setAuth(Boolean canAccess);

	public Boolean isAuth();

	/**
	 * Can retry after auth failed
	 * 
	 * @return
	 */
	public Boolean isAuthRetry();

	public JIDContext setBinding(Boolean isBinding);

	public Boolean isBinding();

	public JIDContext setPriority(Integer priority);

	public Integer getPriority();

	public JIDContext setDomain(String domain);

	public String getDomain();

	public JIDContext setLang(String lang);

	public String getLang();

	/**
	 * Get socket address
	 * 
	 * @return
	 */
	public SocketAddress getAddress();

	public Boolean startTls();

	public Boolean isTls();

	public Status getStatus();

	/**
	 * Clear before reopen XMPP stream
	 * 
	 * @return
	 */
	public JIDContext reset();

	public Boolean close();

	/**
	 * Block writing but not close connection
	 * 
	 * @return
	 */
	public Boolean closePrepare();

	/**
	 * Close connection if ping failed
	 * 
	 * @return
	 */
	public Boolean closeTimeout();

	public JIDContext ping();

	public JIDContext pong(Element element);

	public JIDContext write(Element element);

	public JIDContext write(Collection<Element> elements);
}
