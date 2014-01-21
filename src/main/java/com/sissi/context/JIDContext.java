package com.sissi.context;

import java.net.SocketAddress;
import java.util.Collection;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public Long getIndex();

	public JIDContext setJid(JID jid);

	public JID getJid();

	public JIDContext setAuth(Boolean canAccess);

	public Boolean isAuth();

	public Boolean isAuthRetry();

	public JIDContext setBinding(Boolean isBinding);

	public Boolean isBinding();

	public JIDContext setPriority(Integer priority);

	public Integer getPriority();

	public JIDContext setDomain(String domain);

	public String getDomain();

	public JIDContext setLang(String lang);

	public String getLang();

	public SocketAddress getAddress();

	public Boolean setTls();

	public Boolean isTls();
	
	public JIDContext setPresence();
	
	public Boolean isPresence();
	
	public Status getStatus();

	public JIDContext reset();

	public Boolean close();

	public Boolean closePrepare();

	public Boolean closeTimeout();

	public JIDContext ping();

	public JIDContext pong(Element element);

	public JIDContext write(Element element);

	public JIDContext write(Collection<Element> elements);
}
