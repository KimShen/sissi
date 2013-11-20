package com.sissi.addressing;

<<<<<<< HEAD
import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
=======
import com.sissi.context.JIDContext;
import com.sissi.context.JID;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

<<<<<<< HEAD
	/**
	 * Ban the context out off server
	 * 
	 * @param context
	 */
	public void ban(JIDContext context);

	public void join(JIDContext context);

	public void leave(JIDContext context);

	/**
	 * Find contexts which belong the jid even contain def resource
	 * 
	 * @param jid
	 * @return
	 */
	public Collection<JIDContext> find(JID jid);

	/**
	 * Find prioritiest context which belong the jid
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext findOne(JID jid);

=======
	public JIDContext join(JIDContext context);

	public JIDContext find(JID jid);
	
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	public Boolean isOnline(JID jid);
}
