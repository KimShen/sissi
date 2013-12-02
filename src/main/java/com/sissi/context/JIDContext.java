package com.sissi.context;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public Long getIndex();

	public JIDContext setJid(JID jid);

	public JIDContext setAuth(Boolean canAccess);

	public JIDContext setBinding(Boolean isBinding);

	public JIDContext setPriority(Integer priority);

	public JID getJid();

	public Integer getPriority();

	public MyPresence getPresence();

	public Boolean isAuth();

	public Boolean isBinding();

	public Boolean close();

	public void write(Element element);

	public interface JIDContextBuilder {

		public JIDContext build(JID jid, JIDContextParam param);
	}

	public interface JIDContextParam {

		public <T> T find(String key, Class<T> clazz);
	}

}
