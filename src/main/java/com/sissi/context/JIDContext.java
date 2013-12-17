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

	public JIDContext setPriority(Integer priority);

	public Integer getPriority();

	public JIDContext setStarttls();
	
	public Boolean isStarttls();
	
	public Status getStatus();

	public Boolean close();

	public JIDContext write(Element element);

	public interface JIDContextBuilder {

		public JIDContext build(JID jid, JIDContextParam param);
	}

	public interface JIDContextParam {

		public <T> T find(String key, Class<T> clazz);
	}

	public interface Status {

		public Status setStatus(String type, String show, String status, String avator);

		public StatusClauses getStatus();

		public Status close();
	}

	public interface StatusClauses {

		public final static String KEY_TYPE = "type";

		public final static String KEY_SHOW = "show";

		public final static String KEY_STATUS = "status";

		public final static String KEY_AVATOR = "avator";

		public String find(String key);
	}

	public interface StatusBuilder {

		public Status build(JIDContext context);
	}
}
