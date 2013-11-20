package com.sissi.context;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public JIDContext setAuth(Boolean canAccess);

	public JIDContext setJid(JID jid);

	public JID getJid();

	public MyPresence getPresence();

	public Boolean isAuth();

	/**
	 * JID is logining
	 * 
	 * @return
	 */
	public Boolean isLogining();

	public Boolean close();

	public void write(Protocol protocol);

	public interface MyPresence {

		public String type();

		public String show();

		public String status();
		
		public String type(String type);

		public String show(String show);

		public String status(String status);
	}
}
