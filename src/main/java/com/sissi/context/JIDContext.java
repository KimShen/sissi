package com.sissi.context;

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

	public JIDContext setBinding(Boolean isBinding);

	public Boolean isBinding();
	
	public JIDContext setSession(Boolean isSession);

	public Boolean isSession();

	public JIDContext setPriority(Integer priority);

	public Integer getPriority();

	public MyPresence getPresence();

	public Boolean close();

	public void write(Element element);

	public interface JIDContextBuilder {

		public JIDContext build(JID jid, JIDContextParam param);
	}

	public interface JIDContextParam {

		public <T> T find(String key, Class<T> clazz);
	}
}
