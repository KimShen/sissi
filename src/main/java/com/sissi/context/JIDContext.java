package com.sissi.context;

<<<<<<< HEAD
import com.sissi.protocol.Element;
=======
import com.sissi.protocol.Node;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

<<<<<<< HEAD
	public Long getIndex();
=======
	public JIDContext setAuth(Boolean canAccess);
	
	public JIDContext setBinding(Boolean isBinding);
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

	public JIDContext setJid(JID jid);

	public JIDContext setAuth(Boolean canAccess);

	public JIDContext setBinding(Boolean isBinding);

	public JIDContext setPriority(Integer priority);

	public JID getJid();

	public Integer getPriority();

	public MyPresence getPresence();

	public Boolean isAuth();
	
	public Boolean isBinding();

	public Boolean isBinding();

	public Boolean close();

<<<<<<< HEAD
	public void write(Element element);

	public interface JIDContextBuilder {

		public JIDContext build(JID jid, JIDContextParam param);
	}

	public interface JIDContextParam {

		public <T> T find(String key, Class<T> clazz);
	}

=======
	public void write(Node node);
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
}
