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

		public String getTypeAsText();

		public String getShowAsText();

		public String getStatusAsText();
		
		public String getAvatorAsText();

		public Status asType(String type);

		public Status asShow(String show);

		public Status asStatus(String status);
		
		public Status asAvator(String avator);

		public Status clear();

		public interface StatusBuilder {

			public Status build(JIDContext context);
		}
	}
}
