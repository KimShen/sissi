package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

	public Addressing leave(JID jid);

	public Addressing join(JIDContext context);

	public Addressing leave(JIDContext context);
	
	public Addressing promote(JIDContext context);
	
	public Addressing activate(JIDContext context);

	public JIDContext find(JID jid);

	public JIDContext find(JID jid, Boolean usingResource);

	public JIDContext findOne(JID jid);

	public Integer others(JID jid);
	
	public Integer others(JID jid, Boolean usingResource);
}
